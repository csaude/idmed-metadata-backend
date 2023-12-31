package mz.org.idmed.metadata.district

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class DistrictServiceSpec extends Specification {

    DistrictService districtService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new District(...).save(flush: true, failOnError: true)
        //new District(...).save(flush: true, failOnError: true)
        //District district = new District(...).save(flush: true, failOnError: true)
        //new District(...).save(flush: true, failOnError: true)
        //new District(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //district.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        districtService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<District> districtList = districtService.list(max: 2, offset: 2)

        then:
        districtList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        districtService.count() == 5
    }

    void "test delete"() {
        Long districtId = setupData()

        expect:
        districtService.count() == 5

        when:
        districtService.delete(districtId)
        datastore.currentSession.flush()

        then:
        districtService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        District district = new District()
        districtService.save(district)

        then:
        district.id != null
    }
}
