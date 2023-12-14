package mz.org.idmed.metadata.drug

import grails.gorm.transactions.Transactional
import groovy.util.logging.Slf4j
import mz.org.idmed.metadata.clinicalService.ClinicalService
import mz.org.idmed.metadata.form.Form
import mz.org.idmed.metadata.restUtils.RestClient
import mz.org.idmed.metadata.therapeuticRegimen.TherapeuticRegimen
import org.grails.web.json.JSONArray
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

@Transactional
@EnableScheduling
@Slf4j
class RestGetDrugCentralToolService {

    private static final NAME = "RestGetDrugCentralToolService"


    private static final Logger LOGGER = LoggerFactory
            .getLogger("RestGetDrugCentralToolService");


    RestClient restClient = new RestClient()
    private static final String FORMAT_STRING = '| %1$-10s |  %2$-40s|  %3$-30s|';

    private static final String MESSAGE = String.format(
            FORMAT_STRING,
            "Id Dispensa",
            "Nome",
            "NID");

    static lazyInit = false

 @Scheduled(fixedDelay = 60000L)
 void execute() {
     /*
     def offset = 0
     def count = loadDrugsFromCentralTool(offset).size()

     if (count > 0) {
         offset = ++offset
         print(offset)
         loadDrugsFromCentralTool(offset)
     }
      */
     def offset = 0
     def count

     while (true) {
          count = loadDrugsFromCentralTool(offset).size()

         if (count > 0) {
             offset++
             print(offset)

         } else {
             break // Exit the loop when count becomes zero
         }
     }
 }


    @Transactional
    List<Drug>  loadDrugsFromCentralTool(int offset) {
        def drugList = new ArrayList()
        Drug.withTransaction {
            def regimeList = new ArrayList()
          //  String urlPath = "/api/product?offset=" + offset + "&max=100"
            String urlPath = "/api/product/getProductsByAreas?offset="+offset+"&max=100"
            def response = restClient.requestGetDataOnMetadataServer(urlPath) as JSONArray
            int i = 0
            drugList = response
            for (def drugObject : response) {
                try {
                    if (drugObject.getAt("status").equals("Activo")) {
                        // Check wheather the drug as TARV OR TB Regimen

                        regimeList = drugObject.getAt("therapeuticRegimenList") as ArrayList
                        if (!regimeList.isEmpty()) {
                            for (def regimeTerapeutico in regimeList) {
                                println(regimeTerapeutico.getAt("areaCode"))
                                if (regimeTerapeutico.getAt("areaCode").equals("T") || regimeTerapeutico.getAt("areaCode").equals("TB")) {
                                    def drugExist = Drug.findWhere(fnmCode: drugObject.getAt("fnm"))
                                    if (!drugExist) {
                                        println(i++)
                                        drugExist = saveDrug(drugExist, drugObject, regimeTerapeutico)
                                    }
                                    // drugList.add(drugExist)
                                    def regimeExist = TherapeuticRegimen.findWhere(code: regimeTerapeutico.getAt("code"))
                                    if (!regimeExist) {
                                        regimeExist = new TherapeuticRegimen()
                                        regimeExist.id = regimeTerapeutico.getAt("id")
                                        regimeExist.regimenScheme = regimeTerapeutico.getAt("description")
                                        regimeExist.active = regimeTerapeutico.getAt("status").equals("Activo")
                                        regimeExist.code = regimeTerapeutico.getAt("code")
                                        regimeExist.description = regimeTerapeutico.getAt("description")
                                        regimeExist.openmrsUuid =  regimeTerapeutico.getAt("uuidOpenmrs")
                                        regimeExist.clinicalService = regimeTerapeutico.getAt("areaCode").equals("TB") ? ClinicalService.findWhere(code: "TB") : ClinicalService.findWhere(code: "TARV")
                                        regimeExist.beforeInsert()
                                    }
                                    regimeExist.addToDrugs(drugExist)
                                    TherapeuticRegimen.withTransaction {
                                        println(regimeExist.code)
                                        regimeExist.save(flush:true)
                                    }

                                }
                            }
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
            }
            return drugList
        }


    Drug saveDrug(Drug drugExist, def drugObject, def regimeTerapeutico){
        try {
            drugExist = new Drug()
            drugExist.id = drugObject.getAt("id")
            drugExist.packSize = drugObject.getAt("unitsPerPack") as int
            drugExist.name = drugObject.getAt("fullDescription")
            drugExist.defaultTreatment = 1.0 as double
            drugExist.defaultTimes = 1 as int
            drugExist.defaultPeriodTreatment = regimeTerapeutico.getAt("areaCode").equals("TB") ? "": "Dia"
            drugExist.fnmCode = drugObject.getAt("fnm")
            drugExist.uuidOpenmrs = drugObject.getAt("uuidOpenmrs")
            drugExist.clinicalService = regimeTerapeutico.getAt("areaCode").equals("TB") ? ClinicalService.findWhere(code: "TB") : ClinicalService.findWhere(code: "TARV")
            drugExist.form = findOrSave(drugObject.getAt("pharmaceuticFormDescription") as String)
            drugExist.active = true
            drugExist.beforeInsert()
            drugExist.validate()
            Drug.withTransaction {
                drugExist.save(flush:true)
            }
        }catch (Exception e){
            e.printStackTrace()
        }
        return drugExist
    }

    Form findOrSave(String formDescription){
        def serachParam = formDescription.substring(0, Math.min(formDescription.size(), 4)).trim()

        if(serachParam.equalsIgnoreCase("Emba"))
            serachParam = "Comp"

        List<Form> forms = Form.createCriteria().list {
            ilike('description', "%${serachParam}%")
        } as List<Form>

        def form = forms.size() == 0 ? new Form() : forms.get(0)
        if(form.id == null){
            // form = new Form()
            form.beforeInsert()
            form.code = serachParam
            form.description = formDescription
            Form.withTransaction {
                form.save()
            }
        }

        return form
    }
    }


