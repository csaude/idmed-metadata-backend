package mz.org.idmed.metadata.therapeuticRegimen

import mz.org.idmed.metadata.clinicalService.ClinicalService
import mz.org.idmed.metadata.drug.Drug

class TherapeuticRegimen {

    String id
    String regimenScheme
    boolean active
    String code
    String description
    String openmrsUuid
    static belongsTo = [clinicalService: ClinicalService]
    static hasMany = [drugs: Drug]

    static mapping = {
        id generator: "assigned"
        id column: 'id', index: 'Pk_TherapeuticRegimen_Idx'
    }

    def beforeInsert() {
        if (!id) {
            id = UUID.randomUUID()
        }
    }

    static constraints = {
        code nullable: false, unique: true
        regimenScheme nullable: false
        description nullable: true
        openmrsUuid nullable: true
    }

}
