package mz.org.idmed.metadata.clinicalService

import grails.gorm.services.Service

@Service(ClinicalService)
interface ClinicalServiceService {

    ClinicalService get(Serializable id)

    List<ClinicalService> list(Map args)

    Long count()

    ClinicalService delete(Serializable id)

    ClinicalService save(ClinicalService clinicalService)

}
