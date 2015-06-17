// 
// Decompiled by Procyon v0.5.29
// 

package net.jpountz.xxhash;

import net.jpountz.util.SafeUtils;

final class StreamingXXHash32JavaSafe extends AbstractStreamingXXHash32Java
{
    StreamingXXHash32JavaSafe(final int seed) {
        super(seed);
    }
    
    @Override
    public int getValue() {
        int h32;
        if (this.totalLen >= 16L) {
            h32 = Integer.rotateLeft(this.v1, 1) + Integer.rotateLeft(this.v2, 7) + Integer.rotateLeft(this.v3, 12) + Integer.rotateLeft(this.v4, 18);
        }
        else {
            h32 = this.seed + XXHashConstants.PRIME5;
        }
        h32 += (int)this.totalLen;
        int off;
        for (off = 0; off <= this.memSize - 4; off += 4) {
            h32 += SafeUtils.readIntLE(this.memory, off) * XXHashConstants.PRIME3;
            h32 = Integer.rotateLeft(h32, 17) * XXHashConstants.PRIME4;
        }
        while (off < this.memSize) {
            h32 += (SafeUtils.readByte(this.memory, off) & 0xFF) * XXHashConstants.PRIME5;
            h32 = Integer.rotateLeft(h32, 11) * XXHashConstants.PRIME1;
            ++off;
        }
        h32 ^= h32 >>> 15;
        h32 *= XXHashConstants.PRIME2;
        h32 ^= h32 >>> 13;
        h32 *= XXHashConstants.PRIME3;
        h32 ^= h32 >>> 16;
        return h32;
    }
    
    @Override
    public void update(final byte[] buf, int off, final int len) {
        SafeUtils.checkRange(buf, off, len);
        this.totalLen += len;
        if (this.memSize + len < 16) {
            System.arraycopy(buf, off, this.memory, this.memSize, len);
            this.memSize += len;
            return;
        }
        final int end = off + len;
        if (this.memSize > 0) {
            System.arraycopy(buf, off, this.memory, this.memSize, 16 - this.memSize);
            this.v1 += SafeUtils.readIntLE(this.memory, 0) * XXHashConstants.PRIME2;
            this.v1 = Integer.rotateLeft(this.v1, 13);
            this.v1 *= XXHashConstants.PRIME1;
            this.v2 += SafeUtils.readIntLE(this.memory, 4) * XXHashConstants.PRIME2;
            this.v2 = Integer.rotateLeft(this.v2, 13);
            this.v2 *= XXHashConstants.PRIME1;
            this.v3 += SafeUtils.readIntLE(this.memory, 8) * XXHashConstants.PRIME2;
            this.v3 = Integer.rotateLeft(this.v3, 13);
            this.v3 *= XXHashConstants.PRIME1;
            this.v4 += SafeUtils.readIntLE(this.memory, 12) * XXHashConstants.PRIME2;
            this.v4 = Integer.rotateLeft(this.v4, 13);
            this.v4 *= XXHashConstants.PRIME1;
            off += 16 - this.memSize;
            this.memSize = 0;
        }
        final int limit = end - 16;
        int v1 = this.v1;
        int v2;
        int v3;
        int v4;
        for (v2 = this.v2, v3 = this.v3, v4 = this.v4; off <= limit; off += 4, v2 += SafeUtils.readIntLE(buf, off) * XXHashConstants.PRIME2, v2 = Integer.rotateLeft(v2, 13), v2 *= XXHashConstants.PRIME1, off += 4, v3 += SafeUtils.readIntLE(buf, off) * XXHashConstants.PRIME2, v3 = Integer.rotateLeft(v3, 13), v3 *= XXHashConstants.PRIME1, off += 4, v4 += SafeUtils.readIntLE(buf, off) * XXHashConstants.PRIME2, v4 = Integer.rotateLeft(v4, 13), v4 *= XXHashConstants.PRIME1, off += 4) {
            v1 += SafeUtils.readIntLE(buf, off) * XXHashConstants.PRIME2;
            v1 = Integer.rotateLeft(v1, 13);
            v1 *= XXHashConstants.PRIME1;
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
    
    static class Factory implements StreamingXXHash32.Factory
    {
        public static final StreamingXXHash32.Factory INSTANCE;
        
        @Override
        public StreamingXXHash32 newStreamingHash(final int seed) {
            return new StreamingXXHash32JavaSafe(seed);
        }
        
        static {
            INSTANCE = new Factory();
        }
    }
}