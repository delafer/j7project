// 
// Decompiled by Procyon v0.5.29
// 

package net.jpountz.lz4;

import net.jpountz.util.ByteBufferUtils;
import java.nio.ByteBuffer;
import net.jpountz.util.SafeUtils;

final class LZ4JavaSafeFastDecompressor extends LZ4FastDecompressor
{
    public static final LZ4FastDecompressor INSTANCE;
    
    @Override
    public int decompress(final byte[] src, final int srcOff, final byte[] dest, final int destOff, final int destLen) {
        SafeUtils.checkRange(src, srcOff);
        SafeUtils.checkRange(dest, destOff, destLen);
        if (destLen == 0) {
            if (SafeUtils.readByte(src, srcOff) != 0) {
                throw new LZ4Exception("Malformed input at " + srcOff);
            }
            return 1;
        }
        else {
            final int destEnd = destOff + destLen;
            int sOff = srcOff;
            int dOff = destOff;
            while (true) {
                final int token = SafeUtils.readByte(src, sOff) & 0xFF;
                ++sOff;
                int literalLen = token >>> 4;
                if (literalLen == 15) {
                    byte len = -1;
                    while ((len = SafeUtils.readByte(src, sOff++)) == -1) {
                        literalLen += 255;
                    }
                    literalLen += (len & 0xFF);
                }
                final int literalCopyEnd = dOff + literalLen;
                if (literalCopyEnd > destEnd - 8) {
                    if (literalCopyEnd != destEnd) {
                        throw new LZ4Exception("Malformed input at " + sOff);
                    }
                    LZ4SafeUtils.safeArraycopy(src, sOff, dest, dOff, literalLen);
                    sOff += literalLen;
                    dOff = literalCopyEnd;
                    return sOff - srcOff;
                }
                else {
                    LZ4SafeUtils.wildArraycopy(src, sOff, dest, dOff, literalLen);
                    sOff += literalLen;
                    dOff = literalCopyEnd;
                    final int matchDec = SafeUtils.readShortLE(src, sOff);
                    sOff += 2;
                    final int matchOff = dOff - matchDec;
                    if (matchOff < destOff) {
                        throw new LZ4Exception("Malformed input at " + sOff);
                    }
                    int matchLen = token & 0xF;
                    if (matchLen == 15) {
                        byte len2 = -1;
                        while ((len2 = SafeUtils.readByte(src, sOff++)) == -1) {
                            matchLen += 255;
                        }
                        matchLen += (len2 & 0xFF);
                    }
                    matchLen += 4;
                    final int matchCopyEnd = dOff + matchLen;
                    if (matchCopyEnd > destEnd - 8) {
                        if (matchCopyEnd > destEnd) {
                            throw new LZ4Exception("Malformed input at " + sOff);
                        }
                        LZ4SafeUtils.safeIncrementalCopy(dest, matchOff, dOff, matchLen);
                    }
                    else {
                        LZ4SafeUtils.wildIncrementalCopy(dest, matchOff, dOff, matchCopyEnd);
                    }
                    dOff = matchCopyEnd;
                }
            }
        }
    }
    
    @Override
    public int decompress(ByteBuffer src, final int srcOff, ByteBuffer dest, final int destOff, final int destLen) {
        if (src.hasArray() && dest.hasArray()) {
            return this.decompress(src.array(), srcOff, dest.array(), destOff, destLen);
        }
        src = ByteBufferUtils.inNativeByteOrder(src);
        dest = ByteBufferUtils.inNativeByteOrder(dest);
        ByteBufferUtils.checkRange(src, srcOff);
        ByteBufferUtils.checkRange(dest, destOff, destLen);
        if (destLen == 0) {
            if (ByteBufferUtils.readByte(src, srcOff) != 0) {
                throw new LZ4Exception("Malformed input at " + srcOff);
            }
            return 1;
        }
        else {
            final int destEnd = destOff + destLen;
            int sOff = srcOff;
            int dOff = destOff;
            while (true) {
                final int token = ByteBufferUtils.readByte(src, sOff) & 0xFF;
                ++sOff;
                int literalLen = token >>> 4;
                if (literalLen == 15) {
                    byte len = -1;
                    while ((len = ByteBufferUtils.readByte(src, sOff++)) == -1) {
                        literalLen += 255;
                    }
                    literalLen += (len & 0xFF);
                }
                final int literalCopyEnd = dOff + literalLen;
                if (literalCopyEnd > destEnd - 8) {
                    if (literalCopyEnd != destEnd) {
                        throw new LZ4Exception("Malformed input at " + sOff);
                    }
                    LZ4ByteBufferUtils.safeArraycopy(src, sOff, dest, dOff, literalLen);
                    sOff += literalLen;
                    dOff = literalCopyEnd;
                    return sOff - srcOff;
                }
                else {
                    LZ4ByteBufferUtils.wildArraycopy(src, sOff, dest, dOff, literalLen);
                    sOff += literalLen;
                    dOff = literalCopyEnd;
                    final int matchDec = ByteBufferUtils.readShortLE(src, sOff);
                    sOff += 2;
                    final int matchOff = dOff - matchDec;
                    if (matchOff < destOff) {
                        throw new LZ4Exception("Malformed input at " + sOff);
                    }
                    int matchLen = token & 0xF;
                    if (matchLen == 15) {
                        byte len2 = -1;
                        while ((len2 = ByteBufferUtils.readByte(src, sOff++)) == -1) {
                            matchLen += 255;
                        }
                        matchLen += (len2 & 0xFF);
                    }
                    matchLen += 4;
                    final int matchCopyEnd = dOff + matchLen;
                    if (matchCopyEnd > destEnd - 8) {
                        if (matchCopyEnd > destEnd) {
                            throw new LZ4Exception("Malformed input at " + sOff);
                        }
                        LZ4ByteBufferUtils.safeIncrementalCopy(dest, matchOff, dOff, matchLen);
                    }
                    else {
                        LZ4ByteBufferUtils.wildIncrementalCopy(dest, matchOff, dOff, matchCopyEnd);
                    }
                    dOff = matchCopyEnd;
                }
            }
        }
    }
    
    static {
        INSTANCE = new LZ4JavaSafeFastDecompressor();
    }
}
