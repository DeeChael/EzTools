package lib.ezt.nms.v1_14_R1;

import com.google.gson.*;
import net.minecraft.server.v1_14_R1.*;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.eztools.api.item.TransferTool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class TransferTool_v1_14_R1 implements TransferTool {

    private static boolean isJsonObject(String string) {
        try {
            new JsonParser().parse(string).getAsJsonObject();
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    private static boolean isJsonArray(String string) {
        try {
            new JsonParser().parse(string).getAsJsonArray();
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    private static boolean isJsonObject(JsonElement jsonElement) {
        try {
            jsonElement.getAsJsonObject();
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    private static boolean isJsonArray(JsonElement jsonElement) {
        try {
            jsonElement.getAsJsonArray();
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    @Override
    public org.bukkit.inventory.ItemStack toItemStack(String jsonObjectString) {
        if (isJsonObject(jsonObjectString)) {
            return toItemStack(new JsonParser().parse(jsonObjectString).getAsJsonObject());
        }
        return new org.bukkit.inventory.ItemStack(Material.AIR);
    }

    @Override
    public org.bukkit.inventory.ItemStack toItemStack(JsonObject jsonObject) {
        org.bukkit.inventory.ItemStack itemStack = new org.bukkit.inventory.ItemStack(Material.valueOf(jsonObject.get("type").getAsString().toUpperCase()));
        itemStack.setAmount(jsonObject.get("amount").getAsInt());
        ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
        nmsItemStack.setTag(toNBTTagCompound(jsonObject.getAsJsonObject("nbt")));
        return CraftItemStack.asBukkitCopy(nmsItemStack);
    }

    @Override
    public String toJsonObjectString(org.bukkit.inventory.ItemStack itemStack) {
        return new Gson().toJson(toJsonObject(itemStack));
    }

    @Override
    public JsonObject toJsonObject(org.bukkit.inventory.ItemStack itemStack) {
        ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", itemStack.getType().name().toLowerCase());
        jsonObject.addProperty("amount", itemStack.getAmount());
        if (nmsItemStack.hasTag()) {
            assert nmsItemStack.getTag() != null;
            jsonObject.add("nbt", parseNBTTagCompound(nmsItemStack.getTag()));
        }
        return jsonObject;
    }

    private static NBTTagCompound toNBTTagCompound(JsonObject jsonObject) {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            JsonElement jsonElement = entry.getValue();
            if (jsonElement.isJsonObject()) {
                nbtTagCompound.set(key, toNBTTagCompound(jsonElement.getAsJsonObject()));
            } else if (jsonElement.isJsonArray()) {
                nbtTagCompound.set(key, toNBTTagList(jsonElement.getAsJsonArray()));
            } else if (jsonElement.getAsJsonPrimitive().isString()) {
                String value = jsonElement.getAsString();
                if (value.contains("$")) {
                    String[] splitted = value.split("\\$");
                    switch (splitted[0]) {
                        case "string":
                            String content = splitted[1];
                            if (count(value, "$") > 1) {
                                StringBuilder stringBuilder = new StringBuilder(content);
                                for (int i = 2; i < splitted.length; i++) {
                                    stringBuilder.append("$").append(splitted[i]);
                                }
                                content = stringBuilder.toString();
                            }
                            nbtTagCompound.set(key, new NBTTagString(content));
                            break;
                        case "int":
                            if (count(value, "$") == 1) {
                                int i = Integer.parseInt(splitted[1]);
                                nbtTagCompound.set(key, new NBTTagInt(i));
                            }
                            break;
                        case "long":
                            if (count(value, "$") == 1) {
                                long l = Long.parseLong(splitted[1]);
                                nbtTagCompound.set(key, new NBTTagLong(l));
                            }
                            break;
                        case "float":
                            if (count(value, "$") == 1) {
                                float f = Float.parseFloat(splitted[1]);
                                nbtTagCompound.set(key, new NBTTagFloat(f));
                            }
                            break;
                        case "double":
                            if (count(value, "$") == 1) {
                                double d = Double.parseDouble(splitted[1]);
                                nbtTagCompound.set(key, new NBTTagDouble(d));
                            }
                            break;
                        case "short":
                            if (count(value, "$") == 1) {
                                short s = Short.parseShort(splitted[1]);
                                nbtTagCompound.set(key, new NBTTagShort(s));
                            }
                            break;
                        case "byte":
                            if (count(value, "$") == 1) {
                                byte b = Byte.parseByte(splitted[1]);
                                nbtTagCompound.set(key, new NBTTagByte(b));
                            }
                            break;
                        case "int_array":
                            if (count(value, "$") == 1) {
                                JsonArray intJsonArray = new JsonParser().parse(splitted[1]).getAsJsonArray();
                                List<Integer> intList = new ArrayList<>();
                                for (JsonElement jsonElement1 : intJsonArray) {
                                    intList.add(jsonElement1.getAsInt());
                                }
                                nbtTagCompound.set(key, new NBTTagIntArray(intList));
                            }
                            break;
                        case "long_array":
                            if (count(value, "$") == 1) {
                                JsonArray longJsonArray = new JsonParser().parse(splitted[1]).getAsJsonArray();
                                List<Long> longList = new ArrayList<>();
                                for (JsonElement jsonElement1 : longJsonArray) {
                                    longList.add(jsonElement1.getAsLong());
                                }
                                nbtTagCompound.set(key, new NBTTagLongArray(longList));
                            }
                            break;
                        case "byte_array":
                            if (count(value, "$") == 1) {
                                JsonArray byteJsonArray = new JsonParser().parse(splitted[1]).getAsJsonArray();
                                List<Byte> byteList = new ArrayList<>();
                                for (JsonElement jsonElement1 : byteJsonArray) {
                                    byteList.add(jsonElement1.getAsByte());
                                }
                                nbtTagCompound.set(key, new NBTTagByteArray(byteList));
                            }
                            break;
                    }
                }
            }
        }
        return nbtTagCompound;
    }

    private static NBTTagList toNBTTagList(JsonArray jsonArray) {
        NBTTagList nbtTagList = new NBTTagList();
        for (JsonElement jsonElement : jsonArray) {
            if (jsonElement.isJsonObject()) {
                nbtTagList.add(toNBTTagCompound(jsonElement.getAsJsonObject()));
            } else if (jsonElement.isJsonArray()) {
                nbtTagList.add(toNBTTagList(jsonElement.getAsJsonArray()));
            } else if (jsonElement.getAsJsonPrimitive().isString()) {
                String value = jsonElement.getAsString();
                if (value.contains("$")) {
                    String[] splitted = value.split("\\$");
                    switch (splitted[0]) {
                        case "string":
                            String content = splitted[1];
                            if (count(value, "$") > 1) {
                                StringBuilder stringBuilder = new StringBuilder(content);
                                for (int i = 2; i < splitted.length; i++) {
                                    stringBuilder.append("$").append(splitted[i]);
                                }
                                content = stringBuilder.toString();
                            }
                            nbtTagList.add(new NBTTagString(content));
                            break;
                        case "int":
                            if (count(value, "$") == 1) {
                                int i = Integer.parseInt(splitted[1]);
                                nbtTagList.add(new NBTTagInt(i));
                            }
                            break;
                        case "long":
                            if (count(value, "$") == 1) {
                                long l = Long.parseLong(splitted[1]);
                                nbtTagList.add(new NBTTagLong(l));
                            }
                            break;
                        case "float":
                            if (count(value, "$") == 1) {
                                float f = Float.parseFloat(splitted[1]);
                                nbtTagList.add(new NBTTagFloat(f));
                            }
                            break;
                        case "double":
                            if (count(value, "$") == 1) {
                                double d = Double.parseDouble(splitted[1]);
                                nbtTagList.add(new NBTTagDouble(d));
                            }
                            break;
                        case "short":
                            if (count(value, "$") == 1) {
                                short s = Short.parseShort(splitted[1]);
                                nbtTagList.add(new NBTTagShort(s));
                            }
                            break;
                        case "byte":
                            if (count(value, "$") == 1) {
                                byte b = Byte.parseByte(splitted[1]);
                                nbtTagList.add(new NBTTagByte(b));
                            }
                            break;
                        case "int_array":
                            if (count(value, "$") == 1) {
                                JsonArray intJsonArray = new JsonParser().parse(splitted[1]).getAsJsonArray();
                                List<Integer> intList = new ArrayList<>();
                                for (JsonElement jsonElement1 : intJsonArray) {
                                    intList.add(jsonElement1.getAsInt());
                                }
                                nbtTagList.add(new NBTTagIntArray(intList));
                            }
                            break;
                        case "long_array":
                            if (count(value, "$") == 1) {
                                JsonArray longJsonArray = new JsonParser().parse(splitted[1]).getAsJsonArray();
                                List<Long> longList = new ArrayList<>();
                                for (JsonElement jsonElement1 : longJsonArray) {
                                    longList.add(jsonElement1.getAsLong());
                                }
                                nbtTagList.add(new NBTTagLongArray(longList));
                            }
                            break;
                        case "byte_array":
                            if (count(value, "$") == 1) {
                                JsonArray byteJsonArray = new JsonParser().parse(splitted[1]).getAsJsonArray();
                                List<Byte> byteList = new ArrayList<>();
                                for (JsonElement jsonElement1 : byteJsonArray) {
                                    byteList.add(jsonElement1.getAsByte());
                                }
                                nbtTagList.add(new NBTTagByteArray(byteList));
                            }
                            break;
                    }
                }
            }
        }
        return nbtTagList;
    }

    private static JsonObject parseNBTTagCompound(NBTTagCompound nbtTagCompound) {
        JsonObject nbtJsonObject = new JsonObject();
        for (String key : nbtTagCompound.getKeys()) {
            NBTBase nbtBase = nbtTagCompound.get(key);
            if (nbtBase instanceof NBTTagString) {
                nbtJsonObject.addProperty(key, "string$" + ((NBTTagString) nbtBase).asString());
            } else if (nbtBase instanceof NBTTagInt) {
                nbtJsonObject.addProperty(key, "int$" + ((NBTTagInt) nbtBase).asInt());
            } else if (nbtBase instanceof NBTTagLong) {
                nbtJsonObject.addProperty(key, "long$" + ((NBTTagLong) nbtBase).asLong());
            } else if (nbtBase instanceof NBTTagShort) {
                nbtJsonObject.addProperty(key, "short$" + ((NBTTagShort) nbtBase).asShort());
            } else if (nbtBase instanceof NBTTagByte) {
                nbtJsonObject.addProperty(key, "byte$" + ((NBTTagByte) nbtBase).asByte());
            } else if (nbtBase instanceof NBTTagFloat) {
                nbtJsonObject.addProperty(key, "float$" + ((NBTTagFloat) nbtBase).asFloat());
            } else if (nbtBase instanceof NBTTagDouble) {
                nbtJsonObject.addProperty(key, "double$" + ((NBTTagDouble) nbtBase).asDouble());
            } else if (nbtBase instanceof NBTTagIntArray) {
                int[] ints = ((NBTTagIntArray) nbtBase).getInts();
                StringBuilder stringBuilder = new StringBuilder("[");
                for (int i : ints) {
                    stringBuilder.append(i).append(",");
                }
                String string = stringBuilder.substring(0, stringBuilder.length()) + "]";
                nbtJsonObject.addProperty(key, "int_array$" + string);
            } else if (nbtBase instanceof NBTTagLongArray) {
                long[] longs = ((NBTTagLongArray) nbtBase).getLongs();
                StringBuilder stringBuilder = new StringBuilder("[");
                for (long l : longs) {
                    stringBuilder.append(l).append(",");
                }
                String string = stringBuilder.substring(0, stringBuilder.length()) + "]";
                nbtJsonObject.addProperty(key, "long_array$" + string);
            } else if (nbtBase instanceof NBTTagByteArray) {
                byte[] by = ((NBTTagByteArray) nbtBase).getBytes();
                StringBuilder stringBuilder = new StringBuilder("[");
                for (byte b : by) {
                    stringBuilder.append(b).append(",");
                }
                String string = stringBuilder.substring(0, stringBuilder.length()) + "]";
                nbtJsonObject.addProperty(key, "byte_array$" + string);
            } else if (nbtBase instanceof NBTTagCompound) {
                nbtJsonObject.add(key, parseNBTTagCompound((NBTTagCompound) nbtBase));
            } else if (nbtBase instanceof NBTTagList) {
                NBTTagList nbtTagList = (NBTTagList) nbtBase;
                if (nbtTagList.size() > 0) {
                    nbtJsonObject.add(key, parseNBTTagList(nbtTagList));
                }
            }
        }
        return nbtJsonObject;
    }

    private static JsonArray parseNBTTagList(NBTTagList nbtTagList) {
        JsonArray jsonArray = new JsonArray();
        for (NBTBase base : nbtTagList) {
            if (base instanceof NBTTagString) {
                jsonArray.add("string$" + ((NBTTagString) base).asString());
            } else if (base instanceof NBTTagInt) {
                jsonArray.add("int$" + ((NBTTagInt) base).asInt());
            } else if (base instanceof NBTTagLong) {
                jsonArray.add("long$" + ((NBTTagLong) base).asLong());
            } else if (base instanceof NBTTagShort) {
                jsonArray.add("short$" + ((NBTTagShort) base).asShort());
            } else if (base instanceof NBTTagByte) {
                jsonArray.add("byte$" + ((NBTTagByte) base).asByte());
            } else if (base instanceof NBTTagFloat) {
                jsonArray.add("float$" + ((NBTTagFloat) base).asFloat());
            } else if (base instanceof NBTTagDouble) {
                jsonArray.add("double$" + ((NBTTagDouble) base).asDouble());
            } else if (base instanceof NBTTagIntArray) {
                int[] ints = ((NBTTagIntArray) base).getInts();
                StringBuilder stringBuilder = new StringBuilder("[");
                for (int i : ints) {
                    stringBuilder.append(i).append(",");
                }
                String string = stringBuilder.substring(0, stringBuilder.length()) + "]";
                jsonArray.add("int_array$" + string);
            } else if (base instanceof NBTTagLongArray) {
                long[] longs = ((NBTTagLongArray) base).getLongs();
                StringBuilder stringBuilder = new StringBuilder("[");
                for (long l : longs) {
                    stringBuilder.append(l).append(",");
                }
                String string = stringBuilder.substring(0, stringBuilder.length()) + "]";
                jsonArray.add("long_array$" + string);
            } else if (base instanceof NBTTagByteArray) {
                byte[] by = ((NBTTagByteArray) base).getBytes();
                StringBuilder stringBuilder = new StringBuilder("[");
                for (byte b : by) {
                    stringBuilder.append(b).append(",");
                }
                String string = stringBuilder.substring(0, stringBuilder.length()) + "]";
                jsonArray.add("byte_array$" + string);
            } else if (base instanceof NBTTagCompound) {
                jsonArray.add(parseNBTTagCompound((NBTTagCompound) base));
            } else if (base instanceof NBTTagList) {
                jsonArray.add(parseNBTTagList((NBTTagList) base));
            }
        }
        return jsonArray;
    }

    private static int count(String a, String b) {
        return a.length() - (a.replace(b, "").length());
    }

}
