package mz.org.idmed.metadata.district

import mz.org.idmed.metadata.province.Province

class District {

    String id
    String code
    String description
    Province province

    static belongsTo = [Province]

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
        code nullable: false,unique: ['province']
        description nullable: false
    }
}
