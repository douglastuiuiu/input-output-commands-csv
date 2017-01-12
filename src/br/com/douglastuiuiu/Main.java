package br.com.douglastuiuiu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by douglasg on 06/01/2017.
 */
public class Main {

    static List<City> cities;

    public static void main(String[] args) {

        cities = FileUtils.loadCitiesData();
        Map<CommandEnum, String> commands = new HashMap<>();
        commands.put(CommandEnum.COUNT, "count *");
        commands.put(CommandEnum.DISTINCT_COUNT, "count distinct");
        commands.put(CommandEnum.FILTER, "filter");
        commands.put(CommandEnum.EXIT, "exit");

        try {

            System.err.println("\n-- INITIATED APPLICATION --\n");
            Thread.sleep(500);
            Boolean runnig = true;
            while (runnig) {
                System.out.println("--------------------------------------------------------------------------------------------------------");
                System.out.println("     SUPORTED COMMANDS: " + commands.values());
                System.out.println("     COLUMNS: ibgeid, uf, name, capital, lon, lat, noaccents, alternativenames, microregion, mesoregion");
                System.out.println("WRITE THE COMMANDS:");
                InputStreamReader in = new InputStreamReader(System.in);
                BufferedReader keyboard = new BufferedReader(in);
                String command = keyboard.readLine();

                if (command.equalsIgnoreCase(commands.get(CommandEnum.COUNT))) {
                    System.out.println("RESULT:" + CityService.count());
                } else if (command.contains(commands.get(CommandEnum.FILTER).toLowerCase())) {
                    String property = CityService.buildPropertyName(command);
                    String value = CityService.buildFilterValue(property, command);
                    List<City> result = CityService.search(property, value);
                    System.out.println("RESULT:");
                    for (City city : result) {
                        System.out.println(city);
                    }
                    System.out.println("ITENS: " + result.size());
                } else if (command.contains(commands.get(CommandEnum.DISTINCT_COUNT).toLowerCase())) {
                    String property = command.substring(command.lastIndexOf(" ")).trim();
                    System.out.println("RESULT:\n" + CityService.countDistinct(property));
                } else if (command.equalsIgnoreCase(CommandEnum.EXIT.toString().toLowerCase())) {
                    runnig = false;
                    System.exit(0);
                } else {
                    System.err.println("Incorrect syntax! Type correctly one of the following commands:");
                    System.err.println("('count *', 'count distinct [property]', 'filter [property] [value]', 'exit')");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

    }

}
