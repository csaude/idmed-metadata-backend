package mz.org.idmed.metadata.province

import mz.org.idmed.metadata.district.District

class Province {

    String id
    String code
    String description


    static hasMany = [districts: District]
    static mapping = {
        id generator: "assigned"
        id column: 'id', index: 'Pk_Province_Idx'
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
