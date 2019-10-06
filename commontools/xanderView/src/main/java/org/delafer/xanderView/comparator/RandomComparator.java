package org.delafer.xanderView.comparator;

import org.delafer.xanderView.file.entry.ImageAbstract;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class RandomComparator implements Comparator<ImageAbstract<?>> {
    /**  Lazy-loaded Singleton, by Bill Pugh **/
    private static class Holder {
        private final static Comparator<ImageAbstract<?>> INSTANCE = new RandomComparator();
    }

    public static Comparator<ImageAbstract<?>> instance() {
        return RandomComparator.Holder.INSTANCE;
    }


    private final transient Map<String, Long> stored = new HashMap<>(2048);

    private SecureRandom random;
    /**
     *
     */
    public RandomComparator() {
        try {
            random = SecureRandom.getInstanceStrong();
            random.setSeed(random.generateSeed(24));
        } catch (NoSuchAlgorithmException ignore) {}
    }

    @Override
    public int compare(ImageAbstract<?> o1, ImageAbstract<?> o2) {
        return getId(o1).compareTo(getId(o2));
    }

    private final Long getId(final ImageAbstract<?> ia) {
        Long id = stored.get(ia.name());
        if (null == id) {
            id = random.nextLong();
            stored.put(ia.name(), id);
        }
        return id;
    }
}
