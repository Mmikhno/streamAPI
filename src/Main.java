import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }
        // найти кол-во несовершеннолетних
        long teens = persons.stream().filter(x -> x.getAge() >= 18).count();
        // Получить список фамилий призывников (т.е. мужчин от 18 и до 27 лет)
        List<String> conscripts = persons.stream()
                .filter(x -> x.getAge() >= 18 && x.getAge() <= 27)
                .filter(x -> x.getSex().equals(Sex.MAN))
                .map(x -> x.getFamily())
                .collect(Collectors.toList());
        // Получить отсортированный по фамилии список потенциально работоспособных
        List<Person> workers = persons.stream()
                .filter(x -> x.getEducation().equals(Education.HIGHER))
                .filter(x -> x.getAge() >= 18)
                .filter(x -> x.getSex().equals(Sex.MAN) ? x.getAge() <= 65 : x.getAge() <= 60)
                .sorted(Comparator.comparing(x -> x.getFamily()))
                .collect(Collectors.toList());

        System.out.println("Количество несовершеннолетних: " + teens);
        System.out.println("Количество призывников: " + conscripts.size());
        System.out.println("Количество работоспособных: " + workers.size());
    }
}
