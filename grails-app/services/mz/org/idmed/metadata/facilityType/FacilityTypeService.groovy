package mz.org.idmed.metadata.facilityType

import grails.gorm.services.Service

@Service(FacilityType)
interface FacilityTypeService {

    FacilityType get(Serializable id)

    List<FacilityType> list(Map args)

    Long count()

    FacilityType delete(Serializable id)

    FacilityType save(FacilityType facilityType)

}
