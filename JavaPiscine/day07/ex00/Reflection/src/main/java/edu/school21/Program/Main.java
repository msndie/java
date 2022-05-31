package edu.school21.Program;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		Set<Class> namesSet = findAllClassesUsingClassLoader("edu.school21.testClassOne");
		namesSet.addAll(findAllClassesUsingClassLoader("edu.school21.testClassTwo"));
		if (namesSet.isEmpty()) {
			sc.close();
			return;
		}
		System.out.println("Classes:");
		namesSet.forEach(e -> {
			System.out.println(e.getSimpleName());
		});
		System.out.println("---------------------\nEnter class name:");
		String name;
		try {
			name = sc.nextLine();
		} catch (NoSuchElementException e) {
			sc.close();
			return;
		}
		if (!isContains(name, namesSet)) {
			sc.close();
			return;
		}
		System.out.println("---------------------");
		Class class1 = printClassInfo(namesSet, name);
		Object instance = createInstance(class1, name);
		changeInstance(instance);
		callMethod(instance);
		sc.close();
	}

	private static void callMethod(Object instance) {
		System.out.println("---------------------\nEnter name of the method for call:");
		String name = sc.nextLine();
		Method[] methods = instance.getClass().getDeclaredMethods();
		for (Method method : methods) {
			if (name.equals(method.getName() + "(" + Arrays.toString(method.getParameterTypes())
															.replaceAll("\\[", "")
															.replaceAll("]", "") + ")")) {
				Class[] arr = method.getParameterTypes();
				if (arr.length != 0) {
					for (Class class1 : arr) {
						System.out.println("Enter " + class1.getSimpleName() + " value");
						String newValue = sc.nextLine();
						if (!method.getReturnType().getSimpleName().equals("void")) {
							System.out.print("Method returned: ");
						}
						try {
							if (class1.equals(int.class)) {
								Object value = method.invoke(instance, Integer.parseInt(newValue));
								System.out.println((int)value);
							} else if (class1.equals(String.class)) {
								Object value = method.invoke(instance, newValue);
								System.out.println((String)value);
							}
						} catch (IllegalAccessException | InvocationTargetException e) {
							System.out.println(e.getMessage());
							sc.close();
							System.exit(1);
						}
					}
				} else {
					if (!method.getReturnType().getSimpleName().equals("void")) {
						System.out.print("Method returned: ");
					}
					try {
						Object value = method.invoke(instance);
						System.out.println((String) value);
					} catch (IllegalAccessException | InvocationTargetException e) {
						System.out.println(e.getMessage());
						sc.close();
						System.exit(1);
					}
				}
				break;
			}
		}
	}

	private static void changeInstance(Object instance) {
		boolean updated = false;
		System.out.println("---------------------\nEnter name of the field for changing:");
		Field[] fields = instance.getClass().getDeclaredFields();
		try {
			String name = sc.nextLine();
			for (Field field : fields) {
				if (field.getName().equals(name)) {
					field.setAccessible(true);
					if (field.getType().getSimpleName().equals("String")) {
						System.out.println("Enter String value:");
						String value = sc.nextLine();
						field.set(instance, value);
					} else {
						System.out.println("Enter int value:");
						int value = sc.nextInt();
						sc.nextLine();
						field.set(instance, value);
					}
					updated = true;
				}
			}
			if (updated) {
				System.out.println("Object updated: " + instance);
			} else {
				System.out.println("No such field");
			}
		} catch (NoSuchElementException | IllegalAccessException e) {
			System.out.println(e.getMessage());
			sc.close();
			System.exit(1);
		}
	}

	private static Class printClassInfo(Set<Class> set, String name) {
		Field[] fields = null;
		Method[] methods = null;
		Class class1 = null;

		for (Class tmp : set) {
			if (tmp.getSimpleName().equals(name)) {
				class1 = tmp;
				fields = tmp.getDeclaredFields();
				methods = tmp.getDeclaredMethods();
				break;
			}
		}
		if (fields != null) {
			System.out.println("fields :");
			for (Field field : fields) {
				System.out.println("    " + field.getType().getSimpleName() + " " + field.getName());
			}
		}
		if (methods != null) {
			System.out.println("methods :");
			for (Method method : methods) {
				System.out.print("    " + method.getReturnType().getSimpleName() + " " + method.getName());
				Class[] arr = method.getParameterTypes();
				System.out.print("(");
				for (Class class2 : arr) {
					System.out.print(class2.getSimpleName());
				}
				System.out.println(")");
			}
		}
		System.out.println("---------------------");
		return class1;
	}

	public static Object createInstance(Class class1, String name) {
		System.out.println("Let's create an object.");
		Object instance = null;
		try {
			String first = null;
			String second = null;
			int third = 0;
			instance = class1.newInstance();
			Field[] fields = instance.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				System.out.println(fields[i].getName() + ":");
				if (i == 0) {
					first = sc.nextLine();
					fields[i].setAccessible(true);
					fields[i].set(instance, first);
				} else if (i == 1) {
					second = sc.nextLine();
					fields[i].setAccessible(true);
					fields[i].set(instance, second);
				} else {
					third = sc.nextInt();
					sc.nextLine();
					fields[i].setAccessible(true);
					fields[i].set(instance, third);
				}
			}
			System.out.println("Object created: " + instance);
		} catch (SecurityException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			sc.close();
			System.exit(1);
		}
		return instance;
	}

	private static boolean isContains(String name, Set<Class> set) {
		for (Class c : set) {
			if (name.equals(c.getSimpleName())) {
				return true;
			}
		}
		return false;
	}

	private static Set<Class> findAllClassesUsingClassLoader(String packageName) {
		InputStream stream = ClassLoader.getSystemClassLoader()
			.getResourceAsStream(packageName.replaceAll("[.]", "/"));
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		return reader.lines()
			.filter(line -> line.endsWith(".class"))
			.map(line -> getClass(line, packageName))
			.collect(Collectors.toSet());
	}
 
    private static Class getClass(String className, String packageName) {
		try {
			return Class.forName(packageName + "."
				+ className.substring(0, className.lastIndexOf('.')));
		} catch (ClassNotFoundException e) {
			// handle the exception
		}
		return null;
	}
}
