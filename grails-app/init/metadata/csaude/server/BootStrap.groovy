package metadata.csaude.server

import grails.gorm.transactions.Transactional
import mz.org.idmed.metadata.clinicalService.ClinicalService
import mz.org.idmed.metadata.identifierType.IdentifierType
import mz.org.idmed.metadata.protection.Role
import mz.org.idmed.metadata.protection.SecUser
import mz.org.idmed.metadata.protection.UserRole
import mz.org.idmed.metadata.server.Server
import mz.org.idmed.metadata.serviceAttributeType.ClinicalServiceAttributeType

class BootStrap {

    def init = { servletContext ->

        Server.withTransaction { // initServers()
             }

        Role.withTransaction {
            addInitialUsers()
        }
        IdentifierType.withTransaction { initIdentifierType() }

        ClinicalServiceAttributeType.withTransaction { initClinicalServiceAttributeType() }

        ClinicalService.withTransaction { initClinicalService() }
        //   initiateDrugsList()
     //   initiateRegimensList()
    }

    @Transactional
    void addInitialUsers(){

        def adminRole = Role.findByAuthorityIlike("%ROLE_ADMIN%")


        if (!adminRole) {
            adminRole = new Role('ROLE_ADMIN').save(flush: true, failOnError: true)
            adminRole.save(flush: true, failOnError: true)
        }

      def user = new SecUser('admin', 'admin').save()
        UserRole.create(user, adminRole, true)
    }


    void initServers() {
        for (serverObject in listServers()) {
            if (!Server.findByCodeAndDestination(serverObject.code, serverObject.destination)) {
                Server server = new Server()
                server.id = serverObject.id
                server.urlPath = serverObject.urlPath
                server.port = serverObject.port
                server.destination = serverObject.destination
                server.code = serverObject.code
                server.username = serverObject.username
                server.password = serverObject.password
                server.save(flush: true, failOnError: true)
            }
        }
    }

    List<Object> listServers() {
        List<Object> provincialServerList = new ArrayList<>()
        provincialServerList.add(new LinkedHashMap(id: '7036157a-61c3-4515-9ab8-fc68359d9402', code: '13', urlPath: 'http://172.104.203.103:',  port: '3030', destination: 'C_SAUDE_METADATA', username: 'admin', password: 'admin'))

        return provincialServerList
    }

    List<Object> listIdentifierType() {
        List<Object> identifierTypeList = new ArrayList<>()
        identifierTypeList.add(new LinkedHashMap(id: '8BC2D0A9-9AC4-487B-B71F-F8088B1CB532', code: 'NID', description: 'NID', pattern: '##########/####/#####'))
        identifierTypeList.add(new LinkedHashMap(id: '50D0185F-A115-40D9-BF70-75E3F1F6DD91', code: 'NID_CCR', description: 'NID CCR', pattern: '##########/####/#####'))
        identifierTypeList.add(new LinkedHashMap(id: '9A502C09-5F57-4262-A3D5-CA6B62E0D58F', code: 'NID_PREP', description: 'NID PREP', pattern: '##########/####/#####'))
        identifierTypeList.add(new LinkedHashMap(id: '6C2D9E83-6B6B-4F78-9EE8-30CB19CDDF93', code: 'NID_TB', description: 'NIT', pattern: '##########/####/#####'))

        return identifierTypeList
    }


    List<Object> listClinicalService() {
        List<Object> clinicalServiceList = new ArrayList<>()
        clinicalServiceList.add(new LinkedHashMap(id: '80A7852B-57DF-4E40-90EC-ABDE8403E01F', code: 'TARV', description: 'Tratamento Anti-RetroViral', identifierType: IdentifierType.findById('8BC2D0A9-9AC4-487B-B71F-F8088B1CB532'), active: true))
        clinicalServiceList.add(new LinkedHashMap(id: '6D12193B-7D5D-4665-8FC6-A03855986FBD', code: 'TPT', description: 'Tratamento Preventivo da Tuberculose', identifierType: IdentifierType.findById('50D0185F-A115-40D9-BF70-75E3F1F6DD91'), active: true))
        clinicalServiceList.add(new LinkedHashMap(id: '165C876C-F850-436F-B0BB-80D519056BC3', code: 'PREP', description: 'Tratamento Preventivo à Infecção Pelo HIV', identifierType: IdentifierType.findById('9A502C09-5F57-4262-A3D5-CA6B62E0D58F'), active: true))
        clinicalServiceList.add(new LinkedHashMap(id: 'C2AE49AE-FD70-4E6C-8C96-9131B62ECEDF', code: 'PPE', description: 'Tratamento Após Exposição ao HIV', identifierType: IdentifierType.findById('8BC2D0A9-9AC4-487B-B71F-F8088B1CB532'), active: true))
        clinicalServiceList.add(new LinkedHashMap(id: 'F5FEAD76-3038-4D3D-AC28-D63B9952F022', code: 'TB', description: 'Tratamento da Tuberculose', identifierType: IdentifierType.findById('6C2D9E83-6B6B-4F78-9EE8-30CB19CDDF93'), active: true))

        return clinicalServiceList
    }

    List<Object> listClinicalServiceAttribute() {
        List<Object> clinicalServiceAttributeList = new ArrayList<>()
        clinicalServiceAttributeList.add(new LinkedHashMap(id: '02a8d740-362f-4df8-b5ba-061594d8a44f', clinicalService: '80A7852B-57DF-4E40-90EC-ABDE8403E01F', clinicalServiceAttributeType: 'ff8081817c99e33a017c9ef6f59d0000'))
        clinicalServiceAttributeList.add(new LinkedHashMap(id: '02a8d740-362f-4df8-b5ba-061594d8a44a', clinicalService: '80A7852B-57DF-4E40-90EC-ABDE8403E01F', clinicalServiceAttributeType: 'ff8081817c99e33a017c9ef853610003'))
        clinicalServiceAttributeList.add(new LinkedHashMap(id: '02a8d740-362f-4df8-b5ba-061594d8a44b', clinicalService: '80A7852B-57DF-4E40-90EC-ABDE8403E01F', clinicalServiceAttributeType: 'ff8081817c99e33a017c9ef7ab3f0002'))
        clinicalServiceAttributeList.add(new LinkedHashMap(id: '02a8d740-362f-4df8-b5ba-061594d8a44c', clinicalService: '80A7852B-57DF-4E40-90EC-ABDE8403E01F', clinicalServiceAttributeType: 'ff8081817c99e33a017c9ef760800001'))

        clinicalServiceAttributeList.add(new LinkedHashMap(id: '02a8d740-362f-4df8-b5ba-061594d8a44e', clinicalService: '6D12193B-7D5D-4665-8FC6-A03855986FBD', clinicalServiceAttributeType: 'ff8081817c99e33a017c9ef853610003'))
        clinicalServiceAttributeList.add(new LinkedHashMap(id: '02a8d740-362f-4df8-b5ba-061594d8a44g', clinicalService: '6D12193B-7D5D-4665-8FC6-A03855986FBD', clinicalServiceAttributeType: 'ff8081817c99e33a017c9ef7ab3f0002'))
        clinicalServiceAttributeList.add(new LinkedHashMap(id: '02a8d740-362f-4df8-b5ba-061594d8a44r', clinicalService: '6D12193B-7D5D-4665-8FC6-A03855986FBD', clinicalServiceAttributeType: 'ff8081817c99e33a017c9ef6f59d0000'))

        clinicalServiceAttributeList.add(new LinkedHashMap(id: '02a8d740-362f-4df8-b5ba-061594d8a44i', clinicalService: '165C876C-F850-436F-B0BB-80D519056BC3', clinicalServiceAttributeType: 'ff8081817c99e33a017c9ef6f59d0000'))
        clinicalServiceAttributeList.add(new LinkedHashMap(id: '02a8d740-362f-4df8-b5ba-061594d8a44j', clinicalService: '165C876C-F850-436F-B0BB-80D519056BC3', clinicalServiceAttributeType: 'ff8081817c99e33a017c9ef853610003'))
        clinicalServiceAttributeList.add(new LinkedHashMap(id: '02a8d740-362f-4df8-b5ba-061594d8a44k', clinicalService: '165C876C-F850-436F-B0BB-80D519056BC3', clinicalServiceAttributeType: 'ff8081817c99e33a017c9ef7ab3f0002'))

        clinicalServiceAttributeList.add(new LinkedHashMap(id: '02a8d740-362f-4df8-b5ba-061594d8a44m', clinicalService: 'F5FEAD76-3038-4D3D-AC28-D63B9952F022', clinicalServiceAttributeType: 'ff8081817c99e33a017c9ef6f59d0000'))
        clinicalServiceAttributeList.add(new LinkedHashMap(id: '02a8d740-362f-4df8-b5ba-061594d8a44n', clinicalService: 'F5FEAD76-3038-4D3D-AC28-D63B9952F022', clinicalServiceAttributeType: 'ff8081817c99e33a017c9ef853610003'))
        clinicalServiceAttributeList.add(new LinkedHashMap(id: '02a8d740-362f-4df8-b5ba-061594d8a44o', clinicalService: 'F5FEAD76-3038-4D3D-AC28-D63B9952F022', clinicalServiceAttributeType: 'ff8081817c99e33a017c9ef7ab3f0002'))
        clinicalServiceAttributeList.add(new LinkedHashMap(id: '02a8d740-362f-4df8-b5ba-061594d8a44p', clinicalService: 'F5FEAD76-3038-4D3D-AC28-D63B9952F022', clinicalServiceAttributeType: 'ff8081817c99e33a017c9ef760800001'))
        return clinicalServiceAttributeList
    }

    List<Object> listClinicalServiceAttributeType() {
        List<Object> clinicalServiceAttributeList = new ArrayList<>()
        clinicalServiceAttributeList.add(new LinkedHashMap(id: 'ff8081817c99e33a017c9ef6f59d0000', code: 'THERAPEUTICAL_REGIMEN', description: 'Regime terapeutico'))
        clinicalServiceAttributeList.add(new LinkedHashMap(id: 'ff8081817c99e33a017c9ef853610003', code: 'PRESCRIPTION_CHANGE_MOTIVE', description: 'Motivo de alteracao de prescricao'))
        clinicalServiceAttributeList.add(new LinkedHashMap(id: 'ff8081817c99e33a017c9ef7ab3f0002', code: 'PATIENT_TYPE', description: 'Tipo Paciente'))
        clinicalServiceAttributeList.add(new LinkedHashMap(id: 'ff8081817c99e33a017c9ef760800001', code: 'THERAPEUTICAL_LINE', description: 'Linha terapeutica'))

        return clinicalServiceAttributeList
    }

    void initIdentifierType() {
        for (identifierTypeObject in listIdentifierType()) {
            if (!IdentifierType.findByCode(identifierTypeObject.code)) {
                IdentifierType identifierType = new IdentifierType()
                identifierType.id = identifierTypeObject.id
                identifierType.code = identifierTypeObject.code
                identifierType.description = identifierTypeObject.description
                identifierType.setPattern(identifierTypeObject.pattern)
                identifierType.save(flush: true, failOnError: true)
            }
        }
    }

    void initClinicalServiceAttributeType() {
        for (clinicalServiceAttributeTypeObject in listClinicalServiceAttributeType()) {
            if (!ClinicalServiceAttributeType.findByCode(clinicalServiceAttributeTypeObject.code)) {
                ClinicalServiceAttributeType clinicalServiceAttributeType = new ClinicalServiceAttributeType()
                clinicalServiceAttributeType.id = clinicalServiceAttributeTypeObject.id
                clinicalServiceAttributeType.code = clinicalServiceAttributeTypeObject.code
                clinicalServiceAttributeType.description = clinicalServiceAttributeTypeObject.description
                clinicalServiceAttributeType.save(flush: true, failOnError: true)
            }
        }
    }

    void initClinicalServiceAttribute() {
        for (clinicalServiceAttributeObject in listClinicalServiceAttribute()) {
            try {
                ClinicalService clinicalService = ClinicalService.get(clinicalServiceAttributeObject.clinicalService)
                clinicalService.addToClinicalServiceAttributes(ClinicalServiceAttributeType.get(clinicalServiceAttributeObject.clinicalServiceAttributeType))
                clinicalService.save(flush: true, failOnError: true)
            } catch (Exception e) {
                e.printStackTrace()
            } finally {
                continue
            }
        }
    }

    void initClinicalService() {
        for (clinicalServiceObject in listClinicalService()) {
            if (!ClinicalService.findByCode(clinicalServiceObject.code)) {
                ClinicalService clinicalService = new ClinicalService()
                clinicalService.id = clinicalServiceObject.id
                clinicalService.code = clinicalServiceObject.code
                clinicalService.description = clinicalServiceObject.description
                clinicalService.identifierType = clinicalServiceObject.identifierType
                clinicalService.active = clinicalServiceObject.active
                clinicalService.save(flush: true, failOnError: true)
            }
        }
    }


    def destroy = {
    }
}
