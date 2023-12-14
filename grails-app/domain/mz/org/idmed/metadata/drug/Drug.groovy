package mz.org.idmed.metadata.drug

import mz.org.idmed.metadata.clinicalService.ClinicalService
import mz.org.idmed.metadata.form.Form
import mz.org.idmed.metadata.therapeuticRegimen.TherapeuticRegimen

class Drug {

    String id
    int packSize
    String name
    double defaultTreatment
    int defaultTimes
    String defaultPeriodTreatment
    String fnmCode
    String uuidOpenmrs
    ClinicalService clinicalService
    Form form
    static belongsTo = [TherapeuticRegimen]
    boolean active
    static hasMany = [ therapeuticRegimenList: TherapeuticRegimen]
    static mapping = {
        id generator: "assigned"
        id column: 'id', index: 'Pk_Drug_Idx'
    }
    def beforeInsert() {
        if (!id) {
            id = UUID.randomUUID()
        }
    }

    static constraints = {
        name nullable: false, blank: false
        fnmCode nullable: false, unique: true
        packSize(min: 0)
        uuidOpenmrs nullable: true
        clinicalService nullable: false
        defaultTimes(min:1)
    }
}
