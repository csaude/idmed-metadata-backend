package mz.org.idmed.metadata.clinicalService

import mz.org.idmed.metadata.identifierType.IdentifierType
import mz.org.idmed.metadata.serviceAttributeType.ClinicalServiceAttributeType
import mz.org.idmed.metadata.therapeuticRegimen.TherapeuticRegimen

class ClinicalService {

    String id
    String code
    String description
    IdentifierType identifierType
    boolean active

    static belongsTo = [ClinicalServiceAttributeType]
    static hasMany = [clinicalServiceAttributes: ClinicalServiceAttributeType, therapeuticRegimens: TherapeuticRegimen]

    static mapping = {
        id generator: "assigned"
        id column: 'id', index: 'Pk_ClinicalService_Idx'
    }

    def beforeInsert() {
        if (!id) {
            id = UUID.randomUUID()
        }
    }

    static constraints = {
        code nullable: false, unique: true
        description nullable: false
    }

}
