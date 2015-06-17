//
// Decompiled by Procyon v0.5.29
//

package net.jpountz.xxhash;

import static net.jpountz.xxhash.XXHashConstants.*;
import net.jpountz.util.SafeUtils;
import net.jpountz.util.UnsafeUtils;
final class StreamingXXHash64JavaUnsafe extends AbstractStreamingXXHash64Java
{
    StreamingXXHash64JavaUnsafe(final long seed) {
        super(seed);
    }

    @Override
    public long getValue() {
        long h64;
        if (this.totalLen >= 32L) {
            long v1 = this.v1;
            long v2 = this.v2;
            long v3 = this.v3;
            long v4 = this.v4;
            h64 = Long.rotateLeft(v1, 1) + Long.rotateLeft(v2, 7) + Long.rotateLeft(v3, 12) + Long.rotateLeft(v4, 18);
            v1 *= PRIME64_2;
            v1 = Long.rotateLeft(v1, 31);
            v1 *= PRIME64_1;
            h64 ^= v1;
            h64 = h64 * PRIME64_1 + XXHashConstants.PRIME64_4;
            v2 *= PRIME64_2;
            v2 = Long.rotateLeft(v2, 31);
            v2 *= PRIME64_1;
            h64 ^= v2;
            h64 = h64 * PRIME64_1 + XXHashConstants.PRIME64_4;
            v3 *= PRIME64_2;
            v3 = Long.rotateLeft(v3, 31);
            v3 *= PRIME64_1;
            h64 ^= v3;
            h64 = h64 * PRIME64_1 + XXHashConstants.PRIME64_4;
            v4 *= PRIME64_2;
            v4 = Long.rotateLeft(v4, 31);
            v4 *= PRIME64_1;
            h64 ^= v4;
            h64 = h64 * PRIME64_1 + XXHashConstants.PRIME64_4;
        }
        else {
            h64 = this.seed + PRIME64_5;
        }
        h64 += this.totalLen;
        int off;
        for (off = 0; off <= this.memSize - 8; off += 8) {
            long k1 = UnsafeUtils.readLongLE(this.memory, off);
            k1 *= PRIME64_2;
            k1 = Long.rotateLeft(k1, 31);
            k1 *= PRIME64_1;
            h64 ^= k1;
            h64 = Long.rotateLeft(h64, 27) * PRIME64_1 + XXHashConstants.PRIME64_4;
        }
        if (off <= this.memSize - 4) {
            h64 ^= (UnsafeUtils.readIntLE(this.memory, off) & 0xFFFFFFFFL) * PRIME64_1;
            h64 = Long.rotateLeft(h64, 23) * PRIME64_2 + PRIME64_3;
            off += 4;
        }
        while (off < this.memSize) {
            h64 ^= (this.memory[off] & 0xFF) * PRIME64_5;
            h64 = Long.rotateLeft(h64, 11) * PRIME64_1;
            ++off;
        }
        h64 ^= h64 >>> 33;
        h64 *= PRIME64_2;
        h64 ^= h64 >>> 29;
        h64 *= PRIME64_3;
        h64 ^= h64 >>> 32;
        return h64;
    }

    @Override
    public void update(final byte[] buf, int off, final int len) {
        SafeUtils.checkRange(buf, off, len);
        this.totalLen += len;
        if (this.memSize + len < 32) {
            System.arraycopy(buf, off, this.memory, this.memSize, len);
            this.memSize += len;
            return;
        }
        final int end = off + len;
        if (this.memSize > 0) {
            System.arraycopy(buf, off, this.memory, this.memSize, 32 - this.memSize);
            this.v1 += UnsafeUtils.readLongLE(this.memory, 0) * PRIME64_2;
            this.v1 = Long.rotateLeft(this.v1, 31);
            this.v1 *= PRIME64_1;
            this.v2 += UnsafeUtils.readLongLE(this.memory, 8) * PRIME64_2;
            this.v2 = Long.rotateLeft(this.v2, 31);
            this.v2 *= PRIME64_1;
            this.v3 += UnsafeUtils.readLongLE(this.memory, 16) * PRIME64_2;
            this.v3 = Long.rotateLeft(this.v3, 31);
            this.v3 *= PRIME64_1;
            this.v4 += UnsafeUtils.readLongLE(this.memory, 24) * PRIME64_2;
            this.v4 = Long.rotateLeft(this.v4, 31);
            this.v4 *= PRIME64_1;
            off += 32 - this.memSize;
            this.memSize = 0;
        }
        final int limit = end - 32;
        long v1 = this.v1;
        long v2;
        long v3;
        long v4;
        for (v2 = this.v2, v3 = this.v3, v4 = this.v4; off <= limit; off += 8, v2 += UnsafeUtils.readLongLE(buf, off) * PRIME64_2, v2 = Long.rotateLeft(v2, 31), v2 *= PRIME64_1, off += 8, v3 += UnsafeUtils.readLongLE(buf, off) * PRIME64_2, v3 = Long.rotateLeft(v3, 31), v3 *= PRIME64_1, off += 8, v4 += UnsafeUtils.readLongLE(buf, off) * PRIME64_2, v4 = Long.rotateLeft(v4, 31), v4 *= PRIME64_1, off += 8) {
            v1 += UnsafeUtils.readLongLE(buf, off) * PRIME64_2;
            v1 = Long.rotateLeft(v1, 31);
            v1 *= PRIME64_1;
        }
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.v4 = v4;
        if (off < end) {
            System.arraycopy(buf, off, this.memory, 0, end - off);
            this.memSize = end - off;
        }
    }

    static class Factory implements StreamingXXHash64.Factory
    {
        public static final StreamingXXHash64.Factory INSTANCE;

        @Override
        public StreamingXXHash64 newStreamingHash(final long seed) {
            return new StreamingXXHash64JavaUnsafe(seed);
        }

        static {
            INSTANCE = new Factory();
        }
    }
}
