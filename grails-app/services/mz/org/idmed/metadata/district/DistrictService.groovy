package mz.org.idmed.metadata.district

import grails.gorm.services.Service

@Service(District)
interface DistrictService {

    District get(Serializable id)

    List<District> list(Map args)

    Long count()

    District delete(Serializable id)

    District save(District district)

}
