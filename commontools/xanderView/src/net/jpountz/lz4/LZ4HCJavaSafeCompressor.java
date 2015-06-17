//
// Decompiled by Procyon v0.5.29
//

package net.jpountz.lz4;

import java.util.Arrays;
import net.jpountz.util.ByteBufferUtils;
import java.nio.ByteBuffer;
import net.jpountz.util.SafeUtils;

final class LZ4HCJavaSafeCompressor extends LZ4Compressor
{
    public static final LZ4Compressor INSTANCE;
    private final int maxAttempts;
    final int compressionLevel;
    static final /* synthetic */ boolean $assertionsDisabled = false;

    LZ4HCJavaSafeCompressor() {
        this(LZ4Constants.DEFAULT_COMPRESSION_LEVEL);
    }

    LZ4HCJavaSafeCompressor(final int compressionLevel) {
        this.maxAttempts = 1 << compressionLevel - 1;
        this.compressionLevel = compressionLevel;
    }

    @Override
    public int compress(final byte[] src, final int srcOff, final int srcLen, final byte[] dest, final int destOff, final int maxDestLen) {
        SafeUtils.checkRange(src, srcOff, srcLen);
        SafeUtils.checkRange(dest, destOff, maxDestLen);
        final int srcEnd = srcOff + srcLen;
        final int destEnd = destOff + maxDestLen;
        final int mfLimit = srcEnd - LZ4Constants.MF_LIMIT;
        final int matchLimit = srcEnd - LZ4Constants.LAST_LITERALS;
        int sOff = srcOff;
        int dOff = destOff;
        int anchor = sOff++;
        final HashTable ht = new HashTable(srcOff);
        final LZ4Utils.Match match0 = new LZ4Utils.Match();
        final LZ4Utils.Match match = new LZ4Utils.Match();
        final LZ4Utils.Match match2 = new LZ4Utils.Match();
        final LZ4Utils.Match match3 = new LZ4Utils.Match();
    Label_0101:
        while (sOff < mfLimit) {
            if (ht.insertAndFindBestMatch(src, sOff, matchLimit, match)) {
                LZ4Utils.copyTo(match, match0);
                while (LZ4HCJavaSafeCompressor.$assertionsDisabled || match.start >= anchor) {
                    if (match.end() >= mfLimit || !ht.insertAndFindWiderMatch(src, match.end() - 2, match.start + 1, matchLimit, match.len, match2)) {
                        dOff = LZ4SafeUtils.encodeSequence(src, anchor, match.start, match.ref, match.len, dest, dOff, destEnd);
                        sOff = (anchor = match.end());
                        continue Label_0101;
                    }
                    if (match0.start < match.start && match2.start < match.start + match0.len) {
                        LZ4Utils.copyTo(match0, match);
                    }
                    assert match2.start > match.start;
                    if (match2.start - match.start < 3) {
                        LZ4Utils.copyTo(match2, match);
                    }
                    else {
                        while (true) {
                            if (match2.start - match.start < LZ4Constants.OPTIMAL_ML) {
                                int newMatchLen = match.len;
                                if (newMatchLen > LZ4Constants.OPTIMAL_ML) {
                                    newMatchLen = LZ4Constants.OPTIMAL_ML;
                                }
                                if (match.start + newMatchLen > match2.end() - 4) {
                                    newMatchLen = match2.start - match.start + match2.len - LZ4Constants.MIN_MATCH;
                                }
                                final int correction = newMatchLen - (match2.start - match.start);
                                if (correction > 0) {
                                    match2.fix(correction);
                                }
                            }
                            if (match2.start + match2.len >= mfLimit || !ht.insertAndFindWiderMatch(src, match2.end() - 3, match2.start, matchLimit, match2.len, match3)) {
                                if (match2.start < match.end()) {
                                    match.len = match2.start - match.start;
                                }
                                dOff = LZ4SafeUtils.encodeSequence(src, anchor, match.start, match.ref, match.len, dest, dOff, destEnd);
                                sOff = (anchor = match.end());
                                dOff = LZ4SafeUtils.encodeSequence(src, anchor, match2.start, match2.ref, match2.len, dest, dOff, destEnd);
                                sOff = (anchor = match2.end());
                                continue Label_0101;
                            }
                            if (match3.start < match.end() + 3) {
                                if (match3.start >= match.end()) {
                                    if (match2.start < match.end()) {
                                        final int correction2 = match.end() - match2.start;
                                        match2.fix(correction2);
                                        if (match2.len < LZ4Constants.MIN_MATCH) {
                                            LZ4Utils.copyTo(match3, match2);
                                        }
                                    }
                                    dOff = LZ4SafeUtils.encodeSequence(src, anchor, match.start, match.ref, match.len, dest, dOff, destEnd);
                                    sOff = (anchor = match.end());
                                    LZ4Utils.copyTo(match3, match);
                                    LZ4Utils.copyTo(match2, match0);
                                    break;
                                }
                                LZ4Utils.copyTo(match3, match2);
                            }
                            else {
                                if (match2.start < match.end()) {
                                    if (match2.start - match.start < LZ4Constants.RUN_MASK) {
                                        if (match.len > LZ4Constants.OPTIMAL_ML) {
                                            match.len = LZ4Constants.OPTIMAL_ML;
                                        }
                                        if (match.end() > match2.end() - LZ4Constants.MIN_MATCH) {
                                            match.len = match2.end() - match.start - LZ4Constants.MIN_MATCH;
                                        }
                                        final int correction2 = match.end() - match2.start;
                                        match2.fix(correction2);
                                    }
                                    else {
                                        match.len = match2.start - match.start;
                                    }
                                }
                                dOff = LZ4SafeUtils.encodeSequence(src, anchor, match.start, match.ref, match.len, dest, dOff, destEnd);
                                sOff = (anchor = match.end());
                                LZ4Utils.copyTo(match2, match);
                                LZ4Utils.copyTo(match3, match2);
                            }
                        }
                    }
                }
                throw new AssertionError();
            }
            ++sOff;
        }
        dOff = LZ4SafeUtils.lastLiterals(src, anchor, srcEnd - anchor, dest, dOff, destEnd);
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
        final int srcEnd = srcOff + srcLen;
        final int destEnd = destOff + maxDestLen;
        final int mfLimit = srcEnd - LZ4Constants.MF_LIMIT;
        final int matchLimit = srcEnd - LZ4Constants.LAST_LITERALS;
        int sOff = srcOff;
        int dOff = destOff;
        int anchor = sOff++;
        final HashTable ht = new HashTable(srcOff);
        final LZ4Utils.Match match0 = new LZ4Utils.Match();
        final LZ4Utils.Match match = new LZ4Utils.Match();
        final LZ4Utils.Match match2 = new LZ4Utils.Match();
        final LZ4Utils.Match match3 = new LZ4Utils.Match();
    Label_0148:
        while (sOff < mfLimit) {
            if (ht.insertAndFindBestMatch(src, sOff, matchLimit, match)) {
                LZ4Utils.copyTo(match, match0);
                while (LZ4HCJavaSafeCompressor.$assertionsDisabled || match.start >= anchor) {
                    if (match.end() >= mfLimit || !ht.insertAndFindWiderMatch(src, match.end() - 2, match.start + 1, matchLimit, match.len, match2)) {
                        dOff = LZ4ByteBufferUtils.encodeSequence(src, anchor, match.start, match.ref, match.len, dest, dOff, destEnd);
                        sOff = (anchor = match.end());
                        continue Label_0148;
                    }
                    if (match0.start < match.start && match2.start < match.start + match0.len) {
                        LZ4Utils.copyTo(match0, match);
                    }
                    assert match2.start > match.start;
                    if (match2.start - match.start < 3) {
                        LZ4Utils.copyTo(match2, match);
                    }
                    else {
                        while (true) {
                            if (match2.start - match.start < LZ4Constants.OPTIMAL_ML) {
                                int newMatchLen = match.len;
                                if (newMatchLen > LZ4Constants.OPTIMAL_ML) {
                                    newMatchLen = LZ4Constants.OPTIMAL_ML;
                                }
                                if (match.start + newMatchLen > match2.end() - LZ4Constants.MIN_MATCH) {
                                    newMatchLen = match2.start - match.start + match2.len - LZ4Constants.MIN_MATCH;
                                }
                                final int correction = newMatchLen - (match2.start - match.start);
                                if (correction > 0) {
                                    match2.fix(correction);
                                }
                            }
                            if (match2.start + match2.len >= mfLimit || !ht.insertAndFindWiderMatch(src, match2.end() - 3, match2.start, matchLimit, match2.len, match3)) {
                                if (match2.start < match.end()) {
                                    match.len = match2.start - match.start;
                                }
                                dOff = LZ4ByteBufferUtils.encodeSequence(src, anchor, match.start, match.ref, match.len, dest, dOff, destEnd);
                                sOff = (anchor = match.end());
                                dOff = LZ4ByteBufferUtils.encodeSequence(src, anchor, match2.start, match2.ref, match2.len, dest, dOff, destEnd);
                                sOff = (anchor = match2.end());
                                continue Label_0148;
                            }
                            if (match3.start < match.end() + 3) {
                                if (match3.start >= match.end()) {
                                    if (match2.start < match.end()) {
                                        final int correction2 = match.end() - match2.start;
                                        match2.fix(correction2);
                                        if (match2.len < 4) {
                                            LZ4Utils.copyTo(match3, match2);
                                        }
                                    }
                                    dOff = LZ4ByteBufferUtils.encodeSequence(src, anchor, match.start, match.ref, match.len, dest, dOff, destEnd);
                                    sOff = (anchor = match.end());
                                    LZ4Utils.copyTo(match3, match);
                                    LZ4Utils.copyTo(match2, match0);
                                    break;
                                }
                                LZ4Utils.copyTo(match3, match2);
                            }
                            else {
                                if (match2.start < match.end()) {
                                    if (match2.start - match.start < LZ4Constants.RUN_MASK) {
                                        if (match.len > LZ4Constants.OPTIMAL_ML) {
                                            match.len = LZ4Constants.OPTIMAL_ML;
                                        }
                                        if (match.end() > match2.end() - LZ4Constants.MIN_MATCH) {
                                            match.len = match2.end() - match.start - LZ4Constants.MIN_MATCH;
                                        }
                                        final int correction2 = match.end() - match2.start;
                                        match2.fix(correction2);
                                    }
                                    else {
                                        match.len = match2.start - match.start;
                                    }
                                }
                                dOff = LZ4ByteBufferUtils.encodeSequence(src, anchor, match.start, match.ref, match.len, dest, dOff, destEnd);
                                sOff = (anchor = match.end());
                                LZ4Utils.copyTo(match2, match);
                                LZ4Utils.copyTo(match3, match2);
                            }
                        }
                    }
                }
                throw new AssertionError();
            }
            ++sOff;
        }
        dOff = LZ4ByteBufferUtils.lastLiterals(src, anchor, srcEnd - anchor, dest, dOff, destEnd);
        return dOff - destOff;
    }

    static {
        INSTANCE = new LZ4HCJavaSafeCompressor();
    }

    private class HashTable
    {
        static final int MASK = 65535;
        int nextToUpdate;
        private final int base;
        private final int[] hashTable;
        private final short[] chainTable;

        HashTable(final int base) {
            this.base = base;
            this.nextToUpdate = base;
            Arrays.fill(this.hashTable = new int[LZ4Constants.HASH_TABLE_SIZE_HC], -1);
            this.chainTable = new short[LZ4Constants.MAX_DISTANCE];
        }

        private int hashPointer(final byte[] bytes, final int off) {
            final int v = SafeUtils.readInt(bytes, off);
            return this.hashPointer(v);
        }

        private int hashPointer(final ByteBuffer bytes, final int off) {
            final int v = ByteBufferUtils.readInt(bytes, off);
            return this.hashPointer(v);
        }

        private int hashPointer(final int v) {
            final int h = LZ4Utils.hashHC(v);
            return this.hashTable[h];
        }

        private int next(final int off) {
            return off - (this.chainTable[off & 0xFFFF] & 0xFFFF);
        }

        private void addHash(final byte[] bytes, final int off) {
            final int v = SafeUtils.readInt(bytes, off);
            this.addHash(v, off);
        }

        private void addHash(final ByteBuffer bytes, final int off) {
            final int v = ByteBufferUtils.readInt(bytes, off);
            this.addHash(v, off);
        }

        private void addHash(final int v, final int off) {
            final int h = LZ4Utils.hashHC(v);
            int delta = off - this.hashTable[h];
            assert delta > 0 : delta;
            if (delta >= LZ4Constants.MAX_DISTANCE) {
                delta = 65535;
            }
            this.chainTable[off & 0xFFFF] = (short)delta;
            this.hashTable[h] = off;
        }

        void insert(final int off, final byte[] bytes) {
            while (this.nextToUpdate < off) {
                this.addHash(bytes, this.nextToUpdate);
                ++this.nextToUpdate;
            }
        }

        void insert(final int off, final ByteBuffer bytes) {
            while (this.nextToUpdate < off) {
                this.addHash(bytes, this.nextToUpdate);
                ++this.nextToUpdate;
            }
        }

        boolean insertAndFindBestMatch(final byte[] buf, final int off, final int matchLimit, final LZ4Utils.Match match) {
            match.start = off;
            match.len = 0;
            int delta = 0;
            int repl = 0;
            this.insert(off, buf);
            int ref = this.hashPointer(buf, off);
            if (ref >= off - 4 && ref <= off && ref >= this.base) {
                if (LZ4SafeUtils.readIntEquals(buf, ref, off)) {
                    delta = off - ref;
                    final int len = 4 + LZ4SafeUtils.commonBytes(buf, ref + 4, off + 4, matchLimit);
                    match.len = len;
                    repl = len;
                    match.ref = ref;
                }
                ref = this.next(ref);
            }
            for (int i = 0; i < LZ4HCJavaSafeCompressor.this.maxAttempts && ref >= Math.max(this.base, off - LZ4Constants.MAX_DISTANCE + 1) && ref <= off; ref = this.next(ref), ++i) {
                if (LZ4SafeUtils.readIntEquals(buf, ref, off)) {
                    final int matchLen = 4 + LZ4SafeUtils.commonBytes(buf, ref + 4, off + 4, matchLimit);
                    if (matchLen > match.len) {
                        match.ref = ref;
                        match.len = matchLen;
                    }
                }
            }
            if (repl != 0) {
                int ptr;
                int end;
                for (ptr = off, end = off + repl - 3; ptr < end - delta; ++ptr) {
                    this.chainTable[ptr & 0xFFFF] = (short)delta;
                }
                do {
                    this.chainTable[ptr & 0xFFFF] = (short)delta;
                    this.hashTable[LZ4Utils.hashHC(SafeUtils.readInt(buf, ptr))] = ptr;
                } while (++ptr < end);
                this.nextToUpdate = end;
            }
            return match.len != 0;
        }

        boolean insertAndFindWiderMatch(final byte[] buf, final int off, final int startLimit, final int matchLimit, final int minLen, final LZ4Utils.Match match) {
            match.len = minLen;
            this.insert(off, buf);
            final int delta = off - startLimit;
            for (int ref = this.hashPointer(buf, off), i = 0; i < LZ4HCJavaSafeCompressor.this.maxAttempts && ref >= Math.max(this.base, off - LZ4Constants.MAX_DISTANCE + 1) && ref <= off; ref = this.next(ref), ++i) {
                if (LZ4SafeUtils.readIntEquals(buf, ref, off)) {
                    final int matchLenForward = 4 + LZ4SafeUtils.commonBytes(buf, ref + 4, off + 4, matchLimit);
                    final int matchLenBackward = LZ4SafeUtils.commonBytesBackward(buf, ref, off, this.base, startLimit);
                    final int matchLen = matchLenBackward + matchLenForward;
                    if (matchLen > match.len) {
                        match.len = matchLen;
                        match.ref = ref - matchLenBackward;
                        match.start = off - matchLenBackward;
                    }
                }
            }
            return match.len > minLen;
        }

        boolean insertAndFindBestMatch(final ByteBuffer buf, final int off, final int matchLimit, final LZ4Utils.Match match) {
            match.start = off;
            match.len = 0;
            int delta = 0;
            int repl = 0;
            this.insert(off, buf);
            int ref = this.hashPointer(buf, off);
            if (ref >= off - 4 && ref <= off && ref >= this.base) {
                if (LZ4ByteBufferUtils.readIntEquals(buf, ref, off)) {
                    delta = off - ref;
                    final int len = 4 + LZ4ByteBufferUtils.commonBytes(buf, ref + 4, off + 4, matchLimit);
                    match.len = len;
                    repl = len;
                    match.ref = ref;
                }
                ref = this.next(ref);
            }
            for (int i = 0; i < LZ4HCJavaSafeCompressor.this.maxAttempts && ref >= Math.max(this.base, off - LZ4Constants.MAX_DISTANCE + 1) && ref <= off; ref = this.next(ref), ++i) {
                if (LZ4ByteBufferUtils.readIntEquals(buf, ref, off)) {
                    final int matchLen = 4 + LZ4ByteBufferUtils.commonBytes(buf, ref + 4, off + 4, matchLimit);
                    if (matchLen > match.len) {
                        match.ref = ref;
                        match.len = matchLen;
                    }
                }
            }
            if (repl != 0) {
                int ptr;
                int end;
                for (ptr = off, end = off + repl - 3; ptr < end - delta; ++ptr) {
                    this.chainTable[ptr & 0xFFFF] = (short)delta;
                }
                do {
                    this.chainTable[ptr & 0xFFFF] = (short)delta;
                    this.hashTable[LZ4Utils.hashHC(ByteBufferUtils.readInt(buf, ptr))] = ptr;
                } while (++ptr < end);
                this.nextToUpdate = end;
            }
            return match.len != 0;
        }

        boolean insertAndFindWiderMatch(final ByteBuffer buf, final int off, final int startLimit, final int matchLimit, final int minLen, final LZ4Utils.Match match) {
            match.len = minLen;
            this.insert(off, buf);
            final int delta = off - startLimit;
            for (int ref = this.hashPointer(buf, off), i = 0; i < LZ4HCJavaSafeCompressor.this.maxAttempts && ref >= Math.max(this.base, off - LZ4Constants.MAX_DISTANCE + 1) && ref <= off; ref = this.next(ref), ++i) {
                if (LZ4ByteBufferUtils.readIntEquals(buf, ref, off)) {
                    final int matchLenForward = 4 + LZ4ByteBufferUtils.commonBytes(buf, ref + 4, off + 4, matchLimit);
                    final int matchLenBackward = LZ4ByteBufferUtils.commonBytesBackward(buf, ref, off, this.base, startLimit);
                    final int matchLen = matchLenBackward + matchLenForward;
                    if (matchLen > match.len) {
                        match.len = matchLen;
                        match.ref = ref - matchLenBackward;
                        match.start = off - matchLenBackward;
                    }
                }
            }
            return match.len > minLen;
        }
    }
}