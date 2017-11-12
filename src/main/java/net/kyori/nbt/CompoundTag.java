/*
 * This file is part of nbt, licensed under the MIT License.
 *
 * Copyright (c) 2017 KyoriPowered
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.kyori.nbt;

import net.kyori.blizzard.NonNull;
import net.kyori.blizzard.Nullable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * A compound tag.
 */
public final class CompoundTag extends Tag implements CollectionTag {
  /**
   * The maximum depth.
   */
  public static final int MAX_DEPTH = 512;
  /**
   * The map of tags.
   */
  private final Map<String, Tag> tags = new HashMap<>();

  /**
   * Gets a tag by its key.
   *
   * @param key the key
   * @return the tag, or {@code null}
   */
  @Nullable
  public Tag get(@NonNull final String key) {
    return this.tags.get(key);
  }

  /**
   * Inserts a tag.
   *
   * @param key the key
   * @param tag the tag
   */
  public void put(@NonNull final String key, @NonNull final Tag tag) {
    this.tags.put(key, tag);
  }

  /**
   * Removes a tag.
   *
   * @param key the key
   */
  public void remove(@NonNull final String key) {
    this.tags.remove(key);
  }

  /**
   * Checks if this compound has a tag with the specified key.
   *
   * @param key the key
   * @return {@code true} if this compound has a tag with the specified key
   */
  public boolean contains(@NonNull final String key) {
    return this.tags.containsKey(key);
  }

  @Override
  public int size() {
    return this.tags.size();
  }

  @Override
  public boolean isEmpty() {
    return this.tags.isEmpty();
  }

  /**
   * Gets a set of keys of the entries in this compound tag.
   *
   * @return a set of keys
   */
  public Set<String> keySet() {
    return this.tags.keySet();
  }

  /**
   * Checks if this compound has a tag with the specified key and type.
   *
   * @param key the key
   * @param type the type
   * @return {@code true} if this compound has a tag with the specified key and type
   */
  public boolean contains(@NonNull final String key, @NonNull final TagType type) {
    @Nullable final Tag tag = this.tags.get(key);
    return tag != null && type.test(tag.type());
  }

  /**
   * Gets the tag type of the tag with the specified key.
   *
   * @param key the key
   * @return the tag type, or {@link TagType#END}
   */
  @NonNull
  public TagType type(@NonNull final String key) {
    @Nullable final Tag tag = this.tags.get(key);
    return tag != null ? tag.type() : TagType.END;
  }

  /**
   * Gets a byte.
   *
   * @param key the key
   * @return the byte value, or {@code 0} if this compound does not contain a byte tag
   *     with the specified key, or has a tag with a different type
   */
  public byte getByte(@NonNull final String key) {
    return this.getByte(key, (byte) 0);
  }

  /**
   * Gets a byte.
   *
   * @param key the key
   * @param defaultValue the default value
   * @return the byte value, or {@code defaultValue} if this compound does not contain a byte tag
   *     with the specified key, or has a tag with a different type
   */
  public byte getByte(@NonNull final String key, final byte defaultValue) {
    if(this.contains(key, TagType.BYTE)) {
      return ((NumberTag) this.tags.get(key)).byteValue();
    }
    return defaultValue;
  }

  /**
   * Inserts a byte.
   *
   * @param key the key
   * @param value the value
   */
  public void putByte(@NonNull final String key, final byte value) {
    this.tags.put(key, new ByteTag(value));
  }

  /**
   * Gets a short.
   *
   * @param key the key
   * @return the short value, or {@code 0} if this compound does not contain a short tag
   *     with the specified key, or has a tag with a different type
   */
  public short getShort(@NonNull final String key) {
    return this.getShort(key, (short) 0);
  }

  /**
   * Gets a short.
   *
   * @param key the key
   * @param defaultValue the default value
   * @return the short value, or {@code defaultValue} if this compound does not contain a short tag
   *     with the specified key, or has a tag with a different type
   */
  public short getShort(@NonNull final String key, final short defaultValue) {
    if(this.contains(key, TagType.SHORT)) {
      return ((NumberTag) this.tags.get(key)).shortValue();
    }
    return defaultValue;
  }

  /**
   * Inserts a short.
   *
   * @param key the key
   * @param value the value
   */
  public void putShort(@NonNull final String key, final short value) {
    this.tags.put(key, new ShortTag(value));
  }

  /**
   * Gets an int.
   *
   * @param key the key
   * @return the int value, or {@code 0} if this compound does not contain an int tag
   *     with the specified key, or has a tag with a different type
   */
  public int getInt(@NonNull final String key) {
    return this.getInt(key, 0);
  }

  /**
   * Gets an int.
   *
   * @param key the key
   * @param defaultValue the default value
   * @return the int value, or {@code defaultValue} if this compound does not contain an int tag
   *     with the specified key, or has a tag with a different type
   */
  public int getInt(@NonNull final String key, final int defaultValue) {
    if(this.contains(key, TagType.INT)) {
      return ((NumberTag) this.tags.get(key)).intValue();
    }
    return defaultValue;
  }

  /**
   * Inserts an int.
   *
   * @param key the key
   * @param value the value
   */
  public void putInt(@NonNull final String key, final int value) {
    this.tags.put(key, new IntTag(value));
  }

  /**
   * Gets a long.
   *
   * @param key the key
   * @return the long value, or {@code 0} if this compound does not contain a long tag
   *     with the specified key, or has a tag with a different type
   */
  public long getLong(@NonNull final String key) {
    return this.getLong(key, 0L);
  }

  /**
   * Gets a long.
   *
   * @param key the key
   * @param defaultValue the default value
   * @return the long value, or {@code defaultValue} if this compound does not contain a long tag
   *     with the specified key, or has a tag with a different type
   */
  public long getLong(@NonNull final String key, final long defaultValue) {
    if(this.contains(key, TagType.LONG)) {
      return ((NumberTag) this.tags.get(key)).longValue();
    }
    return defaultValue;
  }

  /**
   * Inserts a long.
   *
   * @param key the key
   * @param value the value
   */
  public void putLong(@NonNull final String key, final long value) {
    this.tags.put(key, new LongTag(value));
  }

  /**
   * Gets a float.
   *
   * @param key the key
   * @return the float value, or {@code 0} if this compound does not contain a float tag
   *     with the specified key, or has a tag with a different type
   */
  public float getFloat(@NonNull final String key) {
    return this.getFloat(key, 0f);
  }

  /**
   * Gets a float.
   *
   * @param key the key
   * @param defaultValue the default value
   * @return the float value, or {@code defaultValue} if this compound does not contain a float tag
   *     with the specified key, or has a tag with a different type
   */
  public float getFloat(@NonNull final String key, final float defaultValue) {
    if(this.contains(key, TagType.FLOAT)) {
      return ((NumberTag) this.tags.get(key)).floatValue();
    }
    return defaultValue;
  }

  /**
   * Inserts a float.
   *
   * @param key the key
   * @param value the value
   */
  public void putFloat(@NonNull final String key, final float value) {
    this.tags.put(key, new FloatTag(value));
  }

  /**
   * Gets a double.
   *
   * @param key the key
   * @return the double value, or {@code 0} if this compound does not contain a double tag
   *     with the specified key, or has a tag with a different type
   */
  public double getDouble(@NonNull final String key) {
    return this.getDouble(key, 0d);
  }

  /**
   * Gets a double.
   *
   * @param key the key
   * @param defaultValue the default value
   * @return the double value, or {@code defaultValue} if this compound does not contain a double tag
   *     with the specified key, or has a tag with a different type
   */
  public double getDouble(@NonNull final String key, final double defaultValue) {
    if(this.contains(key, TagType.DOUBLE)) {
      return ((NumberTag) this.tags.get(key)).doubleValue();
    }
    return defaultValue;
  }

  /**
   * Inserts a double.
   *
   * @param key the key
   * @param value the value
   */
  public void putDouble(@NonNull final String key, final double value) {
    this.tags.put(key, new DoubleTag(value));
  }

  /**
   * Gets an array of bytes.
   *
   * @param key the key
   * @return the array of bytes, or a zero-length array if this compound does not contain a byte array tag
   *     with the specified key, or has a tag with a different type
   */
  @NonNull
  public byte[] getByteArray(@NonNull final String key) {
    if(this.contains(key, TagType.BYTE_ARRAY)) {
      return ((ByteArrayTag) this.tags.get(key)).value();
    }
    return new byte[0];
  }

  /**
   * Gets an array of bytes.
   *
   * @param key the key
   * @param defaultValue the default value
   * @return the array of bytes, or {@code defaultValue}
   */
  @NonNull
  public byte[] getByteArray(@NonNull final String key, @NonNull final byte[] defaultValue) {
    if(this.contains(key, TagType.BYTE_ARRAY)) {
      return ((ByteArrayTag) this.tags.get(key)).value();
    }
    return defaultValue;
  }

  /**
   * Inserts an array of bytes.
   *
   * @param key the key
   * @param value the value
   */
  public void putByteArray(@NonNull final String key, @NonNull final byte[] value) {
    this.tags.put(key, new ByteArrayTag(value));
  }

  /**
   * Gets a string.
   *
   * @param key the key
   * @return the string value, or {@code ""} if this compound does not contain a string tag
   *     with the specified key, or has a tag with a different type
   */
  @NonNull
  public String getString(@NonNull final String key) {
    return this.getString(key, "");
  }

  /**
   * Gets a string.
   *
   * @param key the key
   * @param defaultValue the default value
   * @return the string value, or {@code defaultValue} if this compound does not contain a string tag
   *     with the specified key, or has a tag with a different type
   */
  @NonNull
  public String getString(@NonNull final String key, @NonNull final String defaultValue) {
    if(this.contains(key, TagType.STRING)) {
      return ((StringTag) this.tags.get(key)).value();
    }
    return defaultValue;
  }

  /**
   * Inserts a string.
   *
   * @param key the key
   * @param value the value
   */
  public void putString(@NonNull final String key, @NonNull final String value) {
    this.tags.put(key, new StringTag(value));
  }

  /**
   * Gets a list.
   *
   * @param key the key
   * @return the list, or a new list if this compound does not contain a list tag
   *     with the specified key, or has a tag with a different type
   */
  @NonNull
  public ListTag getList(@NonNull final String key) {
    if(this.contains(key, TagType.LIST)) {
      return (ListTag) this.tags.get(key);
    }
    return new ListTag();
  }

  /**
   * Gets a list, ensuring that the type is the same as {@code type}.
   *
   * @param key the key
   * @param expectedType the expected list type
   * @return the list, or a new list if this compound does not contain a list tag
   *     with the specified key, has a tag with a different type, or the {@link ListTag#listType() list type}
   *     does not match {@code expectedType}
   */
  @NonNull
  public ListTag getList(@NonNull final String key, @NonNull final TagType expectedType) {
    if(this.contains(key, TagType.LIST)) {
      final ListTag tag = (ListTag) this.get(key);
      if(expectedType.test(tag.listType())) {
        return tag;
      }
    }
    return new ListTag();
  }

  /**
   * Gets a list, ensuring that the type is the same as {@code type}.
   *
   * @param key the key
   * @param expectedType the expected list type
   * @param defaultValue the default value
   * @return the list, or {@code defaultValue} if this compound does not contain a list tag
   *     with the specified key, has a tag with a different type, or the {@link ListTag#listType() list type}
   *     does not match {@code expectedType}
   */
  @NonNull
  public ListTag getList(@NonNull final String key, @NonNull final TagType expectedType, @NonNull final ListTag defaultValue) {
    if(this.contains(key, TagType.LIST)) {
      final ListTag tag = (ListTag) this.get(key);
      if(expectedType.test(tag.listType())) {
        return tag;
      }
    }
    return defaultValue;
  }

  /**
   * Gets a list.
   *
   * @param key the key
   * @param defaultValue the default value
   * @return the list, or {@code defaultValue} if this compound does not contain a list tag
   *     with the specified key, or has a tag with a different type
   */
  @NonNull
  public ListTag getList(@NonNull final String key, @NonNull final ListTag defaultValue) {
    if(this.contains(key, TagType.LIST)) {
      return (ListTag) this.tags.get(key);
    }
    return defaultValue;
  }

  /**
   * Gets a compound.
   *
   * @param key the key
   * @return the compound, or a new compound if this compound does not contain a compound tag
   *     with the specified key, or has a tag with a different type
   */
  @NonNull
  public CompoundTag getCompound(@NonNull final String key) {
    if(this.contains(key, TagType.COMPOUND)) {
      return (CompoundTag) this.tags.get(key);
    }
    return new CompoundTag();
  }

  /**
   * Gets a compound.
   *
   * @param key the key
   * @param defaultValue the default value
   * @return the compound, or {@code defaultValue} if this compound does not contain a compound tag
   *     with the specified key, or has a tag with a different type
   */
  @NonNull
  public CompoundTag getCompound(@NonNull final String key, @NonNull final CompoundTag defaultValue) {
    if(this.contains(key, TagType.COMPOUND)) {
      return (CompoundTag) this.tags.get(key);
    }
    return defaultValue;
  }

  /**
   * Gets an array of ints.
   *
   * @param key the key
   * @return the array of ints, or a zero-length array if this compound does not contain a int array tag
   *     with the specified key, or has a tag with a different type
   */
  @NonNull
  public int[] getIntArray(@NonNull final String key) {
    if(this.contains(key, TagType.INT_ARRAY)) {
      return ((IntArrayTag) this.tags.get(key)).value();
    }
    return new int[0];
  }

  /**
   * Gets an array of ints.
   *
   * @param key the key
   * @param defaultValue the default value
   * @return the array of ints, or {@code defaultValue}
   */
  @NonNull
  public int[] getIntArray(@NonNull final String key, @NonNull final int[] defaultValue) {
    if(this.contains(key, TagType.INT_ARRAY)) {
      return ((IntArrayTag) this.tags.get(key)).value();
    }
    return defaultValue;
  }

  /**
   * Inserts an array of ints.
   *
   * @param key the key
   * @param value the value
   */
  public void putIntArray(@NonNull final String key, @NonNull final int[] value) {
    this.tags.put(key, new IntArrayTag(value));
  }

  /**
   * Gets an array of longs.
   *
   * @param key the key
   * @return the array of longs, or a zero-length array if this compound does not contain a long array tag
   *     with the specified key, or has a tag with a different type
   */
  @NonNull
  public long[] getLongArray(@NonNull final String key) {
    if(this.contains(key, TagType.BYTE_ARRAY)) {
      return ((LongArrayTag) this.tags.get(key)).value();
    }
    return new long[0];
  }

  /**
   * Gets an array of longs.
   *
   * @param key the key
   * @param defaultValue the default value
   * @return the array of longs, or {@code defaultValue}
   */
  @NonNull
  public long[] getLongArray(@NonNull final String key, @NonNull final long[] defaultValue) {
    if(this.contains(key, TagType.BYTE_ARRAY)) {
      return ((LongArrayTag) this.tags.get(key)).value();
    }
    return defaultValue;
  }

  /**
   * Inserts an array of longs.
   *
   * @param key the key
   * @param value the value
   */
  public void putLongArray(@NonNull final String key, @NonNull final long[] value) {
    this.tags.put(key, new LongArrayTag(value));
  }

  /**
   * Gets a boolean.
   *
   * <p>A boolean is stored as a {@code byte} internally.</p>
   *
   * @param key the key
   * @return the boolean, or {@code false} if this compound does not contain a boolean with
   *     the specified key, or has a tag with a different type
   */
  public boolean getBoolean(@NonNull final String key) {
    return this.getBoolean(key, false);
  }

  /**
   * Gets a boolean.
   *
   * <p>A boolean is stored as a {@code byte} internally.</p>
   *
   * @param key the key
   * @param defaultValue the default value
   * @return the boolean, or {@code defaultValue} if this compound does not contain a boolean with
   *     the specified key, or has a tag with a different type
   */
  public boolean getBoolean(@NonNull final String key, final boolean defaultValue) {
    // >=, as this can be something other than a byte
    return this.getByte(key, defaultValue ? ByteTag.TRUE : ByteTag.FALSE) >= ByteTag.TRUE;
  }

  /**
   * Inserts a boolean.
   *
   * <p>A boolean is stored as a {@code byte} internally.</p>
   *
   * @param key the key
   * @param value the value
   */
  public void putBoolean(@NonNull final String key, final boolean value) {
    this.putByte(key, value ? ByteTag.TRUE : ByteTag.FALSE);
  }

  /**
   * Checks if this compound has a boolean tag with the specified key.
   *
   * @param key the key
   * @return {@code true} if this compound has a boolean tag with the specified key
   */
  public boolean containsBoolean(@NonNull final String key) {
    return this.contains(key, TagType.BYTE);
  }

  /**
   * Gets a unique id.
   *
   * <p>A unique id is stored as two {@code long}s internally.</p>
   *
   * @param key the key
   * @return the unique id
   */
  @NonNull
  public UUID getUniqueId(@NonNull final String key) {
    return new UUID(this.getLong(key + "Least"), this.getLong(key + "Most"));
  }

  /**
   * Inserts a unique id.
   *
   * <p>A unique id is stored as two {@code long}s internally.</p>
   *
   * @param key the key
   * @param value the value
   */
  public void putUniqueId(@NonNull final String key, @NonNull final UUID value) {
    this.putLong(key + "Least", value.getLeastSignificantBits());
    this.putLong(key + "Most", value.getMostSignificantBits());
  }

  /**
   * Checks if this compound has a unique id tag with the specified key.
   *
   * @param key the key
   * @return {@code true} if this compound has a unique id tag with the specified key
   */
  public boolean containsUniqueId(@NonNull final String key) {
    return this.contains(key + "Least", TagType.LONG) && this.contains(key + "Most", TagType.LONG);
  }

  @Override
  protected void read(final DataInput input, final int depth) throws IOException {
    if(depth > MAX_DEPTH) {
      throw new IllegalStateException(String.format("Depth of %d is higher than max of %d", depth, MAX_DEPTH));
    }

    TagType type;
    while((type = TagType.of(input.readByte())) != TagType.END) {
      final String key = input.readUTF();
      final Tag tag = type.create();
      tag.read(input, depth + 1);
      this.tags.put(key, tag);
    }
  }

  @Override
  protected void write(final DataOutput output) throws IOException {
    for(final String key : this.tags.keySet()) {
      final Tag tag = this.tags.get(key);
      output.writeByte(tag.type().id());
      if(tag.type() != TagType.END) {
        output.writeUTF(key);
        tag.write(output);
      }
    }
    output.writeByte(TagType.END.id());
  }

  @NonNull
  @Override
  public TagType type() {
    return TagType.COMPOUND;
  }

  @Override
  public CompoundTag copy() {
    final CompoundTag copy = new CompoundTag();
    for(final Map.Entry<String, Tag> entry : this.tags.entrySet()) {
      copy.put(entry.getKey(), entry.getValue().copy());
    }
    return copy;
  }

  @Override
  public int hashCode() {
    return this.tags.hashCode();
  }

  @Override
  public boolean equals(final Object that) {
    return this == that || (that instanceof CompoundTag && this.tags.equals(((CompoundTag) that).tags));
  }
}
