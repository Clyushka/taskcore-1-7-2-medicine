import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoFileRepository;
import ru.netology.patient.service.alert.SendAlertServiceImpl;
import ru.netology.patient.service.medical.MedicalService;
import ru.netology.patient.service.medical.MedicalServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MedicalServiceTest {
    @Test
    public void checkBloodPressureTest() {
        PatientInfoFileRepository pifr = Mockito.mock(PatientInfoFileRepository.class);
        SendAlertServiceImpl sas = Mockito.spy(SendAlertServiceImpl.class);

        Mockito.when(pifr.getById(Mockito.anyString()))
                .thenReturn(new PatientInfo(
                        "Name",
                        "Surname",
                        LocalDate.of(1990, 2, 16),
                        new HealthInfo(new BigDecimal(36.6), new BloodPressure(110, 70))));

        MedicalService ms = new MedicalServiceImpl(pifr, sas);
        ms.checkBloodPressure("dfgh678", new BloodPressure(110, 70));
        ms.checkBloodPressure("dfgh678", new BloodPressure(110, 60));

        Mockito.verify(sas, Mockito.only()).send(Mockito.anyString());
    }

    @Test
    public void checkTemperatureTest() {
        PatientInfoFileRepository pifr = Mockito.mock(PatientInfoFileRepository.class);
        SendAlertServiceImpl sas = Mockito.spy(SendAlertServiceImpl.class);

        Mockito.when(pifr.getById(Mockito.anyString()))
                .thenReturn(new PatientInfo(
                        "Name",
                        "Surname",
                        LocalDate.of(1990, 2, 16),
                        new HealthInfo(new BigDecimal(36.6), new BloodPressure(110, 70))));

        MedicalService ms = new MedicalServiceImpl(pifr, sas);
        ms.checkTemperature("dfgh678", new BigDecimal(36.6));
        ms.checkTemperature("dfgh678", new BigDecimal(34));

        Mockito.verify(sas, Mockito.only()).send(Mockito.anyString());
    }

    @Test
    public void checkNormalHealthTest() {
        PatientInfoFileRepository pifr = Mockito.mock(PatientInfoFileRepository.class);
        SendAlertServiceImpl sas = Mockito.spy(SendAlertServiceImpl.class);

        Mockito.when(pifr.getById(Mockito.anyString()))
                .thenReturn(new PatientInfo(
                        "Name",
                        "Surname",
                        LocalDate.of(1990, 2, 16),
                        new HealthInfo(new BigDecimal(36.6), new BloodPressure(110, 70))));

        MedicalService ms = new MedicalServiceImpl(pifr, sas);
        ms.checkBloodPressure("dfgh678", new BloodPressure(110, 70));
        ms.checkTemperature("dfgh678", new BigDecimal(36.5));

        Mockito.verify(sas, Mockito.never()).send(Mockito.anyString());
    }
}
