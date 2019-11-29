package application;
public class Java_SystemProperties {

	public static void main(String[] args) {
		System.out.println("java.vm.name:\t" + System.getProperty("java.vm.name"));
		System.out.println("java.vm.specification.name:\t" + System.getProperty("java.vm.specification.name"));
		System.out.println("java.vm.specification.version:\t" + System.getProperty("java.vm.specification.version"));
		System.out.println("java.vm.specification.vendor:\t" + System.getProperty("java.vm.specification.vendor"));

		System.out.println("os.name:\t" + System.getProperty("os.name"));
		System.out.println("os.version:\t" + System.getProperty("os.version"));
		System.out.println("os.arch:\t" + System.getProperty("os.arch"));
		System.out.println("sun.arch.data.model:\t" + System.getProperty("sun.arch.data.model") + " bit");

		System.out.println("PROCESSOR_IDENTIFIER:\t" + System.getenv("PROCESSOR_IDENTIFIER"));
		System.out.println("PROCESSOR_ARCHITECTURE:\t" + System.getenv("PROCESSOR_ARCHITECTURE"));
		System.out.println("NUMBER_OF_PROCESSORS:\t" + System.getenv("NUMBER_OF_PROCESSORS"));

	}
}