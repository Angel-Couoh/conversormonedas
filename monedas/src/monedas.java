import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


public class monedas {
    private static final String API_KEY = "e3c5d37a576c1edea1106504";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/e3c5d37a576c1edea1106504/pair/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Dólar a Pesos Argentinos");
            System.out.println("2. Pesos Argentinos a Dólar");
            System.out.println("3. Dólar a Real Brasileño");
            System.out.println("4. Real Brasileño a Dólar");
            System.out.println("5. Dólar a Pesos Colombianos");
            System.out.println("6. Pesos Colombianos a Dólar");
            System.out.println("7. Pesos Mexicano a Dólar");
            System.out.println("8. Dólar a Pesos Mexicanos");
            System.out.println("9. Salir");
            int option = scanner.nextInt();
            if (option == 9) {
                break;
            }
            System.out.print("Ingrese la cantidad: ");
            double amount = scanner.nextDouble();
            convertCurrency(option, amount);
        }
        scanner.close();
    }

    private static void convertCurrency(int option, double amount) {
        String fromCurrency = "";
        String toCurrency = "";

        switch (option) {
            case 1:
                fromCurrency = "USD";
                toCurrency = "ARS";
                break;
            case 2:
                fromCurrency = "ARS";
                toCurrency = "USD";
                break;
            case 3:
                fromCurrency = "USD";
                toCurrency = "BRL";
                break;
            case 4:
                fromCurrency = "BRL";
                toCurrency = "USD";
                break;
            case 5:
                fromCurrency = "USD";
                toCurrency = "COP";
                break;
            case 6:
                fromCurrency = "COP";
                toCurrency = "USD";
                break;
            case 7:
                fromCurrency = "MXN";
                toCurrency = "USD";
                break;
            case 8:
                fromCurrency = "USD";
                toCurrency = "MXN";
                break;
            default:
                System.out.println("Opción inválida.");
                return;
        }

        try {
            String urlString = BASE_URL + fromCurrency + "/" + toCurrency;
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("HTTP Response Code: " + responseCode);
            } else {
                Scanner sc = new Scanner(url.openStream());
                StringBuilder inline = new StringBuilder();
                while (sc.hasNext()) {
                    inline.append(sc.nextLine());
                }
                sc.close();

                JSONObject jsonResponse = new JSONObject(inline.toString());
                double conversionRate = jsonResponse.getDouble("conversion_rate");
                double result = amount * conversionRate;

                System.out.println(amount + " " + fromCurrency + " = " + result + " " + toCurrency);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
