package mz.org.idmed.metadata.serviceAttribute

import grails.gorm.services.Service

@Service(ClinicalServiceAttribute)
interface ClinicalServiceAttributeService {

    ClinicalServiceAttribute get(Serializable id)

    List<ClinicalServiceAttribute> list(Map args)

    Long count()

    ClinicalServiceAttribute delete(Serializable id)

    ClinicalServiceAttribute save(ClinicalServiceAttribute clinicalServiceAttribute)

}
