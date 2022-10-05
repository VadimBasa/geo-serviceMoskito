import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;
import ru.netology.sender.MessageSenderImpl;
import java.util.HashMap;
import java.util.Map;
import static ru.netology.geo.GeoServiceImpl.MOSCOW_IP;


@ExtendWith(MockitoExtension.class)

public class CerviseMockitoTest {
    private static final String IP_ADDRESS_HEADER = "x-real-ip" ;
    final String REAL_IP_RUS = "172.0.32.11";
    final String REAL_IP_USA = "96.0.32.11";
    final String USER_ID = "172.0.32.10";
    private MessageSenderImpl messageSenderImpl;
    private Map<String, String> headers;
    Location location1;
    Location location2;
    @Mock
    private GeoService geoService;
    @Mock
    private LocalizationService localizationService;


    @BeforeEach
    void setUp() {
        geoService = Mockito.mock(GeoService.class);
        localizationService = Mockito.mock(LocalizationService.class);
        messageSenderImpl = new MessageSenderImpl(geoService, localizationService);
        headers = new HashMap<String, String>();
        headers.put(IP_ADDRESS_HEADER, REAL_IP_RUS);//"172.123.12.19"
        location1 = new Location("Moscow", Country.RUSSIA, "Lenina", 15);
        location2 = new Location("New York", Country.USA, " 10th Avenue", 32);
    }

    @Test
    void return_Russia_send() {
        Mockito.when(geoService.byIp(MOSCOW_IP)).thenReturn(location1);

        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");

        String actualResult = messageSenderImpl.send(headers);

        String expectedResult = "Добро пожаловать";

        System.out.println(actualResult);

        Assertions.assertEquals(expectedResult, actualResult);
    }
    @Test
    void return_USA_send() {
        Mockito.when(geoService.byIp(REAL_IP_USA)).thenReturn(location2);

        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");
        headers.put(IP_ADDRESS_HEADER, REAL_IP_USA);
        String actualResult = messageSenderImpl.send(headers);

        String expectedResult = "Welcome";

        System.out.println(actualResult);

        Assertions.assertEquals(expectedResult, actualResult);
    }
        @Test

        void byIp() {
            Mockito.when(geoService.byIp(MOSCOW_IP)).thenReturn(location1);

            Location actualResult = geoService.byIp(MOSCOW_IP);

            Location expectedResult = location1;

            Assertions.assertEquals(expectedResult, actualResult);
        }

            @Test
            void locale() {
                Mockito.when(localizationService.locale(Country.BRAZIL)).thenReturn("Welcome");

                String actualResult = localizationService.locale(Country.BRAZIL);

                String expectedResult = "Welcome";

                Assertions.assertEquals(expectedResult, actualResult);
            }



        }