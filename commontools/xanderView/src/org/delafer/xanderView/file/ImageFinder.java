package org.delafer.xanderView.file;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;

import net.j7.commons.io.AbstractFileProcessor;
import net.j7.commons.io.AbstractFileProcessor.FileInfo;

import org.delafer.xanderView.common.NaturalOrderComparator;

public class ImageFinder {

	public static void main(String[] args) {
		ImageFinder.getImages("L:\\best5.CCC\\CURRENT\\sam.posl\\YannaViewOfBarcelona_111408_056xxxl.jpg");
	}

	public static String[] getImages(String fileName) {

		String path = FileUtils.getFilePath(fileName, false);

		final LinkedList<String> files = new LinkedList<String>();
		AbstractFileProcessor scanner = new AbstractFileProcessor(path) {


			@Override
			public boolean accept(File entry, FileInfo fileData) {
				String name = fileData.getNameWithPath().trim().toLowerCase();
				return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".jpe") || name.endsWith(".jfif") || name.endsWith(".jif");
			}

			@Override
			public void processFile(File file, FileInfo fileInfo) throws Exception {
				files.add(fileInfo.getNameWithPath());

			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub

			}
		};
		scanner.setRecurseSubDirectories(false);
		try {
			scanner.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Collections.sort(files, NaturalOrderComparator.INSTANCE);

//		for (String string : files) {
//			System.out.println(string);
//		}
		String[] res = new String[files.size()];
		files.toArray(res);
		return res;
	}

}
