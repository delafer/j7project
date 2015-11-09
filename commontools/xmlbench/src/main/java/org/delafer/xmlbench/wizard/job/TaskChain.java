package org.delafer.xmlbench.wizard.job;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.delafer.xmlbench.config.Helpers;

public class TaskChain {
	
	public static byte TYPE_DEPACK = 2;
	public static byte TYPE_PACK = 1;
	
	private final transient byte[] taskTypes;
	
	private transient AtomicInteger pos = new AtomicInteger(0);
	
	public TaskChain(int depackers, int packers) {
		
		int total = packers + depackers;
		taskTypes = new byte[total];
		
		int p = 0;
		for (int i = 0; i < packers; i++) {
			taskTypes[p] = TYPE_PACK;
			p++;
		}
		
		for (int i = 0; i < depackers; i++) {
			taskTypes[p] = TYPE_DEPACK;
			p++;
		}
		
		if (total > 1) {
		final Random rg = Helpers.getRandomGenerator();
		int neoj = rg.nextInt(4) - 1;
		int swaps = total + neoj;
		int at1, at2;
		for (int i = 0; i <swaps; i++) {
			at1 = rg.nextInt(total);
			while ((at2 = rg.nextInt(total)) == at1) {};
//			System.out.println(at1+ "   "+ at2);
			swap(at1, at2);
		}
		}
		
	}
	
	public byte nextTaskType() {
		if (pos.intValue()>=taskTypes.length) {
			pos.set(0);
		}
		return taskTypes[pos.getAndIncrement()];
	};
	
	public String nextTaskText() {
		byte type =nextTaskType();
		if (type==TYPE_DEPACK) return "depack";
		if (type==TYPE_PACK) return "pack";
		return "unknown";
	};

	private final void swap(int at1, int at2) {
		if (at1==at2) return ;
		byte tmp = taskTypes[at1];
		taskTypes[at1] = taskTypes[at2];
		taskTypes[at2] = tmp;
	}
	
	
	public static void main(String[] args) {
		TaskChain tc = new TaskChain(10, 1);
//		System.out.println(tc);
		for (int i = 0; i < 25; i++) {
			System.out.println(tc.nextTaskText());
		}
	}
	
}
