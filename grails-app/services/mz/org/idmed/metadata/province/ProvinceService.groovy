package mz.org.idmed.metadata.province

import grails.gorm.services.Service

@Service(Province)
interface ProvinceService {

    Province get(Serializable id)

    List<Province> list(Map args)

    Long count()

    Province delete(Serializable id)

    Province save(Province province)

}
