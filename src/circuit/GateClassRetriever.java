package circuit;

import org.reflections.Reflections;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

public class GateClassRetriever
{
    public static List<String> retrieveClassList()
    {
        Reflections reflections = new Reflections("circuit");

        Set<Class<? extends Gate>> allClasses =
                reflections.getSubTypesOf(Gate.class);

        return allClasses.stream()
                         .map(Class::toString)
                         .map(string -> string.substring(6))
                         .collect(toList());
    }
}
