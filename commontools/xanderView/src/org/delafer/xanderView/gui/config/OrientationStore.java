package org.delafer.xanderView.gui.config;


import java.io.*;

import net.j7.commons.io.FilePath;
import net.j7.commons.io.FilePath.PathType;
import net.j7.commons.types.BitNumberRange;
import net.j7.commons.types.Range;

import org.delafer.xanderView.orientation.OrientationCommons.Orientation;

import com.carrotsearch.hppc.LongLongHashMap;
import com.carrotsearch.hppc.procedures.LongLongProcedure;


public class OrientationStore {


	private String fileName;
	private LongLongHashMap map;
	private boolean dirty;
	private final static Range rangeOrient = Range.range(0, 4); // 0 - 15
	/**
	 * Lazy-loaded Singleton, by Bill Pugh *.
	 */
	private static final class Holder {
		/** The Constant INSTANCE. */
		private final static transient OrientationStore INSTANCE = new OrientationStore();
	}

	/**
	 * Gets the single instance of ResourcesDR.
	 *
	 * @return single instance of ResourcesDR
	 */
	public static final OrientationStore instance() {
		return Holder.INSTANCE;
	}

	private OrientationStore() {
		map  = new LongLongHashMap();
		dirty =false;
		init();
	}

	private void init() {
		fileName = FilePath.as().dir(PathType.ApplicationData).dir(ApplConfiguration.XANDER_VIEW).file("imagedata.bin").forceExists().build();
	}

	public void load() throws IOException {

		FileInputStream fos = new FileInputStream(new File(fileName));
		final DataInputStream dos = new DataInputStream(new BufferedInputStream(fos));


		try {
			while (true) {
				long key = dos.readLong();
				long value = dos.readLong();
				map.put(key, value);
			}
		} catch (EOFException e) {
			//reached end of the file
		}



		dos.close();

	}

	public void save() throws IOException {

		if (!dirty) return ;

		dirty = false;

		FileOutputStream fos = new FileOutputStream(new File(fileName));
		final DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(fos));

		map.forEach(new LongLongProcedure() {

			@Override
			public void apply(long key, long value)  {
				try {
					dos.writeLong(key);
					dos.writeLong(value);
				} catch (IOException e) {
					e.printStackTrace();
				}


			}
		});

		dos.flush();
		dos.close();

	}

	private int enumOrderNum(Enum<?> enumVal) {
		return enumVal != null ? enumVal.ordinal()+1 : 0;
	}

	private Orientation enumByNum(int num) {
		if (num == 0) return null;
		return Orientation.values()[--num];
	}

	public void setOrientation(long crc, Orientation orient) {
		long valueOld = map.get(crc);
		long valueNew = BitNumberRange.set(valueOld, rangeOrient,  enumOrderNum(orient));
		if (valueOld == valueNew) return ;
		if (valueNew != 0l) {
			map.put(crc, valueNew);
		} else {
			map.remove(crc);
		}
		dirty =true;
	}

	public Orientation getOrientation(long crc) {
		long value = map.get(crc);
		value = BitNumberRange.value(value, rangeOrient);
		Orientation orient = enumByNum((int)value);
		return orient;
	}


//	public static void main(String[] args) {
//		OrientationStore os = OrientationStore.instance();
//
//
////		for (int i = 0; i < 30000; i++) {
////	          long crc = RandomUtil.getRandom().nextLong();
////	          int orient = RandomUtil.getRandomInt(0, 7);
////			os.setOrientation(crc, Orientation.values()[orient]);
////		}
//
//		try {
//			os.load();
//
//			OrientationStore.instance().map.forEach(new LongLongProcedure() {
//
//				@Override
//				public void apply(long key, long value)  {
//					try {
//						System.out.println(key+" : "+value);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//
//
//				}
//			});
//			System.out.println("done");
//			System.out.println(OrientationStore.instance().map.size());
//			System.out.println(OrientationStore.instance().getOrientation(1062222676426126259l));
//			System.out.println(OrientationStore.instance().getOrientation(2313283709819813184l));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}

}
