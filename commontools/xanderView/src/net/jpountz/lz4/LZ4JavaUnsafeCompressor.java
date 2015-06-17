//
// Decompiled by Procyon v0.5.29
//

package net.jpountz.lz4;

import net.jpountz.util.ByteBufferUtils;
import java.nio.ByteBuffer;
import java.util.Arrays;
import net.jpountz.util.UnsafeUtils;

final class LZ4JavaUnsafeCompressor extends LZ4Compressor
{
    public static final LZ4Compressor INSTANCE;

    static int compress64k(final byte[] src, final int srcOff, final int srcLen, final byte[] dest, final int destOff, final int destEnd) {
        final int srcEnd = srcOff + srcLen;
        final int srcLimit = srcEnd - 5;
        final int mflimit = srcEnd - LZ4Constants.MF_LIMIT;
        int sOff = srcOff;
        int dOff = destOff;
        int anchor = sOff;
        Label_0494: {
            if (srcLen >= LZ4Constants.HASH_LOG_64K) {
                final short[] hashTable = new short[LZ4Constants.HASH_TABLE_SIZE_64K];
                ++sOff;
                while (true) {
                    int forwardOff = sOff;
                    int step = 1;
                    int searchMatchNb = 1 << LZ4Constants.SKIP_STRENGTH;
                    int ref;
                    do {
                        sOff = forwardOff;
                        forwardOff += step;
                        step = searchMatchNb++ >>> LZ4Constants.SKIP_STRENGTH;
                        if (forwardOff > mflimit) {
                            break Label_0494;
                        }
                        final int h = LZ4Utils.hash64k(UnsafeUtils.readInt(src, sOff));
                        ref = srcOff + UnsafeUtils.readShort(hashTable, h);
                        UnsafeUtils.writeShort(hashTable, h, sOff - srcOff);
                    } while (!LZ4UnsafeUtils.readIntEquals(src, ref, sOff));
                    final int excess = LZ4UnsafeUtils.commonBytesBackward(src, ref, sOff, srcOff, anchor);
                    sOff -= excess;
                    ref -= excess;
                    final int runLen = sOff - anchor;
                    int tokenOff = dOff++;
                    if (dOff + runLen + 8 + (runLen >>> 8) > destEnd) {
                        throw new LZ4Exception("maxDestLen is too small");
                    }
                    if (runLen >= LZ4Constants.RUN_MASK) {
                        UnsafeUtils.writeByte(dest, tokenOff, 240);
                        dOff = LZ4UnsafeUtils.writeLen(runLen - LZ4Constants.RUN_MASK, dest, dOff);
                    }
                    else {
                        UnsafeUtils.writeByte(dest, tokenOff, runLen << 4);
                    }
                    LZ4UnsafeUtils.wildArraycopy(src, anchor, dest, dOff, runLen);
                    dOff += runLen;
                    while (true) {
                        UnsafeUtils.writeShortLE(dest, dOff, (short)(sOff - ref));
                        dOff += 2;
                        sOff += 4;
                        ref += 4;
                        final int matchLen = LZ4UnsafeUtils.commonBytes(src, ref, sOff, srcLimit);
                        if (dOff + 6 + (matchLen >>> 8) > destEnd) {
                            throw new LZ4Exception("maxDestLen is too small");
                        }
                        sOff += matchLen;
                        if (matchLen >= LZ4Constants.RUN_MASK) {
                            UnsafeUtils.writeByte(dest, tokenOff, UnsafeUtils.readByte(dest, tokenOff) | 0xF);
                            dOff = LZ4UnsafeUtils.writeLen(matchLen - LZ4Constants.RUN_MASK, dest, dOff);
                        }
                        else {
                            UnsafeUtils.writeByte(dest, tokenOff, UnsafeUtils.readByte(dest, tokenOff) | matchLen);
                        }
                        if (sOff > mflimit) {
                            anchor = sOff;
                            break Label_0494;
                        }
                        UnsafeUtils.writeShort(hashTable, LZ4Utils.hash64k(UnsafeUtils.readInt(src, sOff - 2)), sOff - 2 - srcOff);
                        final int h2 = LZ4Utils.hash64k(UnsafeUtils.readInt(src, sOff));
                        ref = srcOff + UnsafeUtils.readShort(hashTable, h2);
                        UnsafeUtils.writeShort(hashTable, h2, sOff - srcOff);
                        if (!LZ4UnsafeUtils.readIntEquals(src, sOff, ref)) {
                            anchor = sOff++;
                            break;
                        }
                        tokenOff = dOff++;
                        UnsafeUtils.writeByte(dest, tokenOff, 0);
                    }
                }
            }
        }
        dOff = LZ4UnsafeUtils.lastLiterals(src, anchor, srcEnd - anchor, dest, dOff, destEnd);
        return dOff - destOff;
    }

    @Override
    public int compress(final byte[] src, final int srcOff, final int srcLen, final byte[] dest, final int destOff, final int maxDestLen) {
        UnsafeUtils.checkRange(src, srcOff, srcLen);
        UnsafeUtils.checkRange(dest, destOff, maxDestLen);
        final int destEnd = destOff + maxDestLen;
        if (srcLen < LZ4Constants.LZ4_64K_LIMIT) {
            return compress64k(src, srcOff, srcLen, dest, destOff, destEnd);
        }
        final int srcEnd = srcOff + srcLen;
        final int srcLimit = srcEnd - LZ4Constants.LAST_LITERALS;
        final int mflimit = srcEnd - LZ4Constants.MF_LIMIT;
        int sOff = srcOff;
        int dOff = destOff;
        int anchor = sOff++;
        final int[] hashTable = new int[LZ4Constants.HASH_TABLE_SIZE];
        Arrays.fill(hashTable, anchor);
    Label_0560:
        while (true) {
            int forwardOff = sOff;
            int step = 1;
            int searchMatchNb = 1 << LZ4Constants.SKIP_STRENGTH;
            int back;
            int ref;
            do {
                sOff = forwardOff;
                forwardOff += step;
                step = searchMatchNb++ >>> LZ4Constants.SKIP_STRENGTH;
                if (forwardOff > mflimit) {
                    break Label_0560;
                }
                final int h = LZ4Utils.hash(UnsafeUtils.readInt(src, sOff));
                ref = UnsafeUtils.readInt(hashTable, h);
                back = sOff - ref;
                UnsafeUtils.writeInt(hashTable, h, sOff);
            } while (back >= LZ4Constants.MAX_DISTANCE || !LZ4UnsafeUtils.readIntEquals(src, ref, sOff));
            final int excess = LZ4UnsafeUtils.commonBytesBackward(src, ref, sOff, srcOff, anchor);
            sOff -= excess;
            ref -= excess;
            final int runLen = sOff - anchor;
            int tokenOff = dOff++;
            if (dOff + runLen + 8 + (runLen >>> 8) > destEnd) {
                throw new LZ4Exception("maxDestLen is too small");
            }
            if (runLen >= LZ4Constants.RUN_MASK) {
                UnsafeUtils.writeByte(dest, tokenOff, 240);
                dOff = LZ4UnsafeUtils.writeLen(runLen - LZ4Constants.RUN_MASK, dest, dOff);
            }
            else {
                UnsafeUtils.writeByte(dest, tokenOff, runLen << 4);
            }
            LZ4UnsafeUtils.wildArraycopy(src, anchor, dest, dOff, runLen);
            dOff += runLen;
            while (true) {
                UnsafeUtils.writeShortLE(dest, dOff, back);
                dOff += 2;
                sOff += 4;
                final int matchLen = LZ4UnsafeUtils.commonBytes(src, ref + 4, sOff, srcLimit);
                if (dOff + 6 + (matchLen >>> 8) > destEnd) {
                    throw new LZ4Exception("maxDestLen is too small");
                }
                sOff += matchLen;
                if (matchLen >= LZ4Constants.RUN_MASK) {
                    UnsafeUtils.writeByte(dest, tokenOff, UnsafeUtils.readByte(dest, tokenOff) | 0xF);
                    dOff = LZ4UnsafeUtils.writeLen(matchLen - LZ4Constants.RUN_MASK, dest, dOff);
                }
                else {
                    UnsafeUtils.writeByte(dest, tokenOff, UnsafeUtils.readByte(dest, tokenOff) | matchLen);
                }
                if (sOff > mflimit) {
                    anchor = sOff;
                    break Label_0560;
                }
                UnsafeUtils.writeInt(hashTable, LZ4Utils.hash(UnsafeUtils.readInt(src, sOff - 2)), sOff - 2);
                final int h2 = LZ4Utils.hash(UnsafeUtils.readInt(src, sOff));
                ref = UnsafeUtils.readInt(hashTable, h2);
                UnsafeUtils.writeInt(hashTable, h2, sOff);
                back = sOff - ref;
                if (back >= LZ4Constants.MAX_DISTANCE || !LZ4UnsafeUtils.readIntEquals(src, ref, sOff)) {
                    anchor = sOff++;
                    break;
                }
                tokenOff = dOff++;
                UnsafeUtils.writeByte(dest, tokenOff, 0);
            }
        }
        dOff = LZ4UnsafeUtils.lastLiterals(src, anchor, srcEnd - anchor, dest, dOff, destEnd);
        return dOff - destOff;
    }

    static int compress64k(final ByteBuffer src, final int srcOff, final int srcLen, final ByteBuffer dest, final int destOff, final int destEnd) {
        final int srcEnd = srcOff + srcLen;
        final int srcLimit = srcEnd - LZ4Constants.LAST_LITERALS;
        final int mflimit = srcEnd - LZ4Constants.MF_LIMIT;
        int sOff = srcOff;
        int dOff = destOff;
        int anchor = sOff;
        Label_0494: {
            if (srcLen >= LZ4Constants.HASH_LOG_64K) {
                final short[] hashTable = new short[LZ4Constants.HASH_TABLE_SIZE_64K];
                ++sOff;
                while (true) {
                    int forwardOff = sOff;
                    int step = 1;
                    int searchMatchNb = 1 << LZ4Constants.SKIP_STRENGTH;
                    int ref;
                    do {
                        sOff = forwardOff;
                        forwardOff += step;
                        step = searchMatchNb++ >>> LZ4Constants.SKIP_STRENGTH;
                        if (forwardOff > mflimit) {
                            break Label_0494;
                        }
                        final int h = LZ4Utils.hash64k(ByteBufferUtils.readInt(src, sOff));
                        ref = srcOff + UnsafeUtils.readShort(hashTable, h);
                        UnsafeUtils.writeShort(hashTable, h, sOff - srcOff);
                    } while (!LZ4ByteBufferUtils.readIntEquals(src, ref, sOff));
                    final int excess = LZ4ByteBufferUtils.commonBytesBackward(src, ref, sOff, srcOff, anchor);
                    sOff -= excess;
                    ref -= excess;
                    final int runLen = sOff - anchor;
                    int tokenOff = dOff++;
                    if (dOff + runLen + 8 + (runLen >>> 8) > destEnd) {
                        throw new LZ4Exception("maxDestLen is too small");
                    }
                    if (runLen >= LZ4Constants.RUN_MASK) {
                        ByteBufferUtils.writeByte(dest, tokenOff, 240);
                        dOff = LZ4ByteBufferUtils.writeLen(runLen - LZ4Constants.RUN_MASK, dest, dOff);
                    }
                    else {
                        ByteBufferUtils.writeByte(dest, tokenOff, runLen << 4);
                    }
                    LZ4ByteBufferUtils.wildArraycopy(src, anchor, dest, dOff, runLen);
                    dOff += runLen;
                    while (true) {
                        ByteBufferUtils.writeShortLE(dest, dOff, (short)(sOff - ref));
                        dOff += 2;
                        sOff += 4;
                        ref += 4;
                        final int matchLen = LZ4ByteBufferUtils.commonBytes(src, ref, sOff, srcLimit);
                        if (dOff + 6 + (matchLen >>> 8) > destEnd) {
                            throw new LZ4Exception("maxDestLen is too small");
                        }
                        sOff += matchLen;
                        if (matchLen >= LZ4Constants.RUN_MASK) {
                            ByteBufferUtils.writeByte(dest, tokenOff, ByteBufferUtils.readByte(dest, tokenOff) | 0xF);
                            dOff = LZ4ByteBufferUtils.writeLen(matchLen - LZ4Constants.RUN_MASK, dest, dOff);
                        }
                        else {
                            ByteBufferUtils.writeByte(dest, tokenOff, ByteBufferUtils.readByte(dest, tokenOff) | matchLen);
                        }
                        if (sOff > mflimit) {
                            anchor = sOff;
                            break Label_0494;
                        }
                        UnsafeUtils.writeShort(hashTable, LZ4Utils.hash64k(ByteBufferUtils.readInt(src, sOff - 2)), sOff - 2 - srcOff);
                        final int h2 = LZ4Utils.hash64k(ByteBufferUtils.readInt(src, sOff));
                        ref = srcOff + UnsafeUtils.readShort(hashTable, h2);
                        UnsafeUtils.writeShort(hashTable, h2, sOff - srcOff);
                        if (!LZ4ByteBufferUtils.readIntEquals(src, sOff, ref)) {
                            anchor = sOff++;
                            break;
                        }
                        tokenOff = dOff++;
                        ByteBufferUtils.writeByte(dest, tokenOff, 0);
                    }
                }
            }
        }
        dOff = LZ4ByteBufferUtils.lastLiterals(src, anchor, srcEnd - anchor, dest, dOff, destEnd);
        return dOff - destOff;
    }

    @Override
    public int compress(ByteBuffer src, final int srcOff, final int srcLen, ByteBuffer dest, final int destOff, final int maxDestLen) {
        if (src.hasArray() && dest.hasArray()) {
            return this.compress(src.array(), srcOff, srcLen, dest.array(), destOff, maxDestLen);
        }
        src = ByteBufferUtils.inNativeByteOrder(src);
        dest = ByteBufferUtils.inNativeByteOrder(dest);
        ByteBufferUtils.checkRange(src, srcOff, srcLen);
        ByteBufferUtils.checkRange(dest, destOff, maxDestLen);
        final int destEnd = destOff + maxDestLen;
        if (srcLen < LZ4Constants.LZ4_64K_LIMIT) {
            return compress64k(src, srcOff, srcLen, dest, destOff, destEnd);
        }
        final int srcEnd = srcOff + srcLen;
        final int srcLimit = srcEnd - 5;
        final int mflimit = srcEnd - LZ4Constants.MF_LIMIT;
        int sOff = srcOff;
        int dOff = destOff;
        int anchor = sOff++;
        final int[] hashTable = new int[LZ4Constants.HASH_TABLE_SIZE];
        Arrays.fill(hashTable, anchor);
    Label_0607:
        while (true) {
            int forwardOff = sOff;
            int step = 1;
            int searchMatchNb = 1 << LZ4Constants.SKIP_STRENGTH;
            int back;
            int ref;
            do {
                sOff = forwardOff;
                forwardOff += step;
                step = searchMatchNb++ >>> LZ4Constants.SKIP_STRENGTH;
                if (forwardOff > mflimit) {
                    break Label_0607;
                }
                final int h = LZ4Utils.hash(ByteBufferUtils.readInt(src, sOff));
                ref = UnsafeUtils.readInt(hashTable, h);
                back = sOff - ref;
                UnsafeUtils.writeInt(hashTable, h, sOff);
            } while (back >= LZ4Constants.MAX_DISTANCE || !LZ4ByteBufferUtils.readIntEquals(src, ref, sOff));
            final int excess = LZ4ByteBufferUtils.commonBytesBackward(src, ref, sOff, srcOff, anchor);
            sOff -= excess;
            ref -= excess;
            final int runLen = sOff - anchor;
            int tokenOff = dOff++;
            if (dOff + runLen + 8 + (runLen >>> 8) > destEnd) {
                throw new LZ4Exception("maxDestLen is too small");
            }
            if (runLen >= LZ4Constants.RUN_MASK) {
                ByteBufferUtils.writeByte(dest, tokenOff, 240);
                dOff = LZ4ByteBufferUtils.writeLen(runLen - LZ4Constants.RUN_MASK, dest, dOff);
            }
            else {
                ByteBufferUtils.writeByte(dest, tokenOff, runLen << 4);
            }
            LZ4ByteBufferUtils.wildArraycopy(src, anchor, dest, dOff, runLen);
            dOff += runLen;
            while (true) {
                ByteBufferUtils.writeShortLE(dest, dOff, back);
                dOff += 2;
                sOff += 4;
                final int matchLen = LZ4ByteBufferUtils.commonBytes(src, ref + 4, sOff, srcLimit);
                if (dOff + 6 + (matchLen >>> 8) > destEnd) {
                    throw new LZ4Exception("maxDestLen is too small");
                }
                sOff += matchLen;
                if (matchLen >= LZ4Constants.RUN_MASK) {
                    ByteBufferUtils.writeByte(dest, tokenOff, ByteBufferUtils.readByte(dest, tokenOff) | 0xF);
                    dOff = LZ4ByteBufferUtils.writeLen(matchLen - LZ4Constants.RUN_MASK, dest, dOff);
                }
                else {
                    ByteBufferUtils.writeByte(dest, tokenOff, ByteBufferUtils.readByte(dest, tokenOff) | matchLen);
                }
                if (sOff > mflimit) {
                    anchor = sOff;
                    break Label_0607;
                }
                UnsafeUtils.writeInt(hashTable, LZ4Utils.hash(ByteBufferUtils.readInt(src, sOff - 2)), sOff - 2);
                final int h2 = LZ4Utils.hash(ByteBufferUtils.readInt(src, sOff));
                ref = UnsafeUtils.readInt(hashTable, h2);
                UnsafeUtils.writeInt(hashTable, h2, sOff);
                back = sOff - ref;
                if (back >= LZ4Constants.MAX_DISTANCE || !LZ4ByteBufferUtils.readIntEquals(src, ref, sOff)) {
                    anchor = sOff++;
                    break;
                }
                tokenOff = dOff++;
                ByteBufferUtils.writeByte(dest, tokenOff, 0);
            }
        }
        dOff = LZ4ByteBufferUtils.lastLiterals(src, anchor, srcEnd - anchor, dest, dOff, destEnd);
        return dOff - destOff;
    }

    static {
        INSTANCE = new LZ4JavaUnsafeCompressor();
    }
}