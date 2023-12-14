package mz.org.idmed.metadata.serviceAttribute

import mz.org.idmed.metadata.clinicalService.ClinicalService
import mz.org.idmed.metadata.serviceAttributeType.ClinicalServiceAttributeType


class ClinicalServiceAttribute  {
    String id
    ClinicalServiceAttributeType clinicalServiceAttributeType

    static belongsTo = [clinicalService: ClinicalService]

    static mapping = {
        id generator: "assigned"
    }

    static constraints = {
    }

    def beforeInsert() {
        if (!id) {
            id = UUID.randomUUID()
        }
    }

}
