package mz.org.idmed.metadata.clinic

import grails.gorm.services.Service

@Service(Clinic)
interface ClinicService {

    Clinic get(Serializable id)

    List<Clinic> list(Map args)

    Long count()

    Clinic delete(Serializable id)

    Clinic save(Clinic clinic)

}
