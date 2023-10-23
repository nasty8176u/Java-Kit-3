import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import task1iterator.MyGenericCollection;
import task2calculator.CalculatorUtils;
import task3compare.ArrayUtils;
import task4pair.Pair;

public class App {

    public static void main(String[] args) {

        Task1.run();
        Task2.run();
        Task3.run();
        Task4.run();
    }

}

class Task1 {

    private static final int NULLS_FREQ = 5; // each 5th
    private static final int VALUE_BOUND = 1000;
    private static final int N_ADD = 20;
    private static final int N_REMOVE = 3;

    static void run() {
        ConsoleUtils.printlnEmphasized("\nОбобщённая коллекция и её итератор:");

        var collection = new MyGenericCollection<Integer>();

        System.out.print("\nДобавляемые элементы: ");

        for (int i = 0; i < N_ADD; ++i) {
            Integer item = null;
            if (ThreadLocalRandom.current().nextInt(NULLS_FREQ + 1) > 0) {
                item = ThreadLocalRandom.current().nextInt(VALUE_BOUND);
            }
            collection.add(item);
            System.out.print(item);
            System.out.print("  ");
        }

        System.out.println();
        System.out.print("Полученная коллекция: ");
        System.out.println(collection.toString());

        System.out.print("\nУдаляемые элементы: ");

        for (int i = 0; i < N_REMOVE && !collection.isEmpty(); ++i) {
            var item = collection.get(ThreadLocalRandom.current().nextInt(collection.count()));

            collection.removeAll(item);
            System.out.print(item);
            System.out.print("  ");
        }

        System.out.println();
        System.out.print("Полученная коллекция: ");
        System.out.println(collection.toString());

        System.out.println("Вывод элементов коллекции с помощью итератора:");

        for (Integer item : collection) {
            System.out.print(item);
            System.out.print("  ");
        }
        System.out.println();

        var iter = collection.iterator();
        while (iter.hasNext()) {
            System.out.print(iter.next());
            System.out.print("  ");
        }
        System.out.println();
    }
}

class Task2 {

    static void run() {
        ConsoleUtils.printlnEmphasized("\nРабота обобщённых статических методов Калькулятора:\n");

        Number result;
        result = CalculatorUtils.sum(Integer.valueOf(15), Float.valueOf(29.159f));
        System.out.printf("Integer 15 + Float 29.159f = %s as Float\n", result.floatValue());

        result = CalculatorUtils.divide(Double.valueOf(123.456789), Long.valueOf(24));
        System.out.printf("Double 123.456789 / Long 24 = %s as Double\n", result.doubleValue());
        System.out.printf("Double 123.456789 / Long 24 = %s as Long\n", result.longValue());

        result = CalculatorUtils.multiply(Byte.valueOf((byte) 11), Short.valueOf((short) 32000));
        System.out.printf("Byte 11 * Short 32000 = %s as Integer\n", result.intValue());

        result = CalculatorUtils.subtract(Short.valueOf((short) 789), Double.valueOf(1000.4837));
        System.out.printf("Short 789 - Double 1000.4837 = %s as Double\n", result.doubleValue());
        System.out.printf("Short 789 - Double 1000.4837 = %s as Integer\n", result.intValue());
    }
}

class Task3 {

    private static final int VALUE_BOUND = 10000;
    private static final int N_NULLS = 3;

    static record ItemType(Integer value) {
        @Override
        public String toString() {
            return String.format("~%s~", value != null ? value.toString() : "Null");
        }
    }

    static void run() {
        ConsoleUtils.printlnEmphasized("\nРабота обобщённого метода compareArrays():\n");

        var rnd = ThreadLocalRandom.current();

        ItemType[] arrayA = new ItemType[20];
        // good values
        IntStream.range(0, arrayA.length).forEach(i -> {
            arrayA[i] = new ItemType(rnd.nextInt(VALUE_BOUND));
        });
        // contains null
        IntStream.range(0, N_NULLS).forEach(i -> {
            arrayA[rnd.nextInt(0, arrayA.length)] = new ItemType(null);
        });
        // nulls
        IntStream.range(0, N_NULLS).forEach(i -> {
            arrayA[rnd.nextInt(0, arrayA.length)] = null;
        });

        ItemType[] arrayB = new ItemType[20];
        IntStream.range(0, arrayA.length).forEach(i -> {
            arrayB[i] = arrayA[i] != null ? new ItemType(arrayA[i].value()) : null;
        });

        System.out.println("Исходные массивы:");
        System.out.println("arrayA: " + Arrays.toString(arrayA));
        System.out.println("arrayB: " + Arrays.toString(arrayB));
        System.out.println(
                ArrayUtils.<ItemType>compareArrays(arrayA, arrayB)
                        ? "Массивы одинаковые."
                        : "Массивы различаются!");

        arrayB[arrayB.length / 2] = new ItemType(VALUE_BOUND);
                        System.out.println("\nИсходные массивы:");
        System.out.println("arrayA: " + Arrays.toString(arrayA));
        System.out.println("arrayB: " + Arrays.toString(arrayB));
        System.out.println(
                ArrayUtils.<ItemType>compareArrays(arrayA, arrayB)
                        ? "Массивы одинаковые."
                        : "Массивы различаются!");
    }
}

class Task4 {

    static void run() {
        ConsoleUtils.printlnEmphasized("\nОбобщённый класс Pair:\n");

        var pair = new Pair<>(12345, "Текст");
        System.out.println(pair);

        var pair2 = new Pair<Float, Character[]>(456.235f, new Character[] { 'a', 'b', 'C' }, null, Arrays::toString);
        System.out.println(pair2);

        var pair3 = new Pair<Integer, Double>(null, null);
        System.out.println(pair3);
    }
}

class ConsoleUtils {
    static void printlnEmphasized(String text) {
        System.out.println("\u001b[1;4;97m" + text + "\u001b[0m");
    }
}