package mz.org.idmed.metadata.clinic

import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import mz.org.idmed.metadata.clinicalService.ClinicalService
import mz.org.idmed.metadata.drug.Drug
import mz.org.idmed.metadata.form.Form
import mz.org.idmed.metadata.restUtils.RestClient
import mz.org.idmed.metadata.therapeuticRegimen.TherapeuticRegimen
import org.grails.web.json.JSONArray
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

// @Transactional
@EnableScheduling
@Slf4j
@CompileStatic
class RestUpdateClinicCentralToolService {

    private static final NAME = "RestGetDrugCentralToolService"


    private static final Logger LOGGER = LoggerFactory
            .getLogger("LOGGER");


    RestClient restClient = new RestClient()
    private static final String FORMAT_STRING = '| %1$-10s |  %2$-40s|  %3$-30s|';


    static lazyInit = false

    @Scheduled(cron = "0 0 0 2 * ?")
    void execute() {
        def offset = 0
        def count
        LOGGER.info("Iniciando a Busca de Clinicas")
        while (true) {
            count = loadClinicsFromCentralTool(offset).size()

            if (count > 0) {
                offset++
                print(offset)

            } else {
                LOGGER.info("Fim da Busca de Clinicas")
                break // Exit the loop when count becomes zero
            }
        }
    }


    @Transactional
    List<Drug> loadClinicsFromCentralTool(int offset) {

        def clientList = new ArrayList()
        Clinic.withTransaction {
            String urlPath = "/api/clients?offset=" + offset + "&max=100"
            def response = restClient.requestGetDataOnMetadataServer(urlPath) as JSONArray
            int i = 0
            clientList = response
            def clinics = Clinic.findAll()
            for (def clientObject : response) {
                try {
                    clinics.each { clinic ->
                        String code = clientObject.getAt("code")
                        String normalizedString = code.replaceFirst("^0+", "")
                        if (clinic.code.equals(normalizedString)) {
                            clinic.matchFC = clinic.code
                            clinic.save(flush: true, failOnError: true)
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace()
                } catch (ConnectException e1) {
                    e1.printStackTrace()
                } finally {
                    continue
                }
            }
            return clientList
        }


    }

}
