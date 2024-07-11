#!groovy
import java.text.SimpleDateFormat

def dateFormat = new SimpleDateFormat("yyyyMMddHHmm")
def date = new Date()
def timestamp = dateFormat.format(date).toString()
def CORREOFALLAS = "mcalifau@choucairtesting.com"
def CORREOEXITO = "mcalifau@choucairtesting.com;consultor.900221.006.pe@zoluxiones-contractor.com;bgabriel@banbif.com.pe;lfajardoq@choucairtesting.com;ramarism@choucairtesting.com;aromeroc@choucairtesting.com;lmattas@choucairtesting.com;gzeballos@banbif.com.pe;mgaray@banbif.com.pe"
def CORREO = "";
def firmaCorreo = "<br><p>Agradeciendo la atencion prestada. </p><p>Miguel Angel Califa Urquiza</p><p>Analista de pruebas</p><p>CHOUCAIR TESTING S.A.</p><p>www.choucairtesting.com</p><p>3185771114</p><p>Carrera 16a # 79 &ndash; 05 Of 203</p><p>Edificio Office Class</p><p>Bogota-Colombia</p><p></p><p>La informaci&oacute;n contenida en este mensaje as&iacute; como la informaci&oacute;n que contienen sus archivos adjuntos es informaci&oacute;n privilegiada y confidencial y pertenece de forma exclusiva a CHOUCAIR CARDENAS TESTING S.A. Se informa adem&aacute;s que esta dirigida &uacute;nica y exclusivamente a su destinatario, por lo que revelarla o copiarla a terceras personas le esta prohibido. Si usted no es el destinatario de este mensaje, por favor destruya su contenido y avise al remitente, en caso de no hacerlo, queda notificado que la utilizaci&oacute;n, divulgaci&oacute;n y/o copia del mismo esta prohibida seg&uacute;n las normas legales aplicables, pudiendo ser sujeto de sanciones, incluidas las penales. CHOUCAIR CARDENAS TESTING S.A., no asume responsabilidad alguna por una eventual transmisi&oacute;n de virus o da&ntilde;os que se puedan ocasionar por el recibo y uso de esta informaci&oacute;n o la de sus archivos adjuntos, teniendo el deber y la responsabilidad el destinatario de verificar por sus propios medios la existencia de este tipo de elementos. <br>&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash;&mdash; <br>The information contained in this message and the information contained in its attachments thereto is privilege and confidential and it belongs exclusively to CHOUCAIR CARDENAS TESTINGS.A. It is also informed that it&rsquo;s exclusively addressed to its recipient(s), so disclose it or copying it to third parties is prohibited. If you are not the exclusive addressee of this message, please destroy its content and notify the sender, you are hereby notified that any use, disclosure and/or copy thereof is prohibited by law, and may also be subject of criminal sanctions. CHOUCAIR CARDENAS TESTINGS.A., does not assume any responsibility for eventual transmissions of virus or harmful defects that may cause the files contained in this message or the use of them, giving the duty and responsibility to the addressee to verify by its own means the existence of such elements.</p>";



pipeline {
    agent any
    environment {
        D_WORKSPACE = "D:/jenkinsws"
    }
    stages {
        stage('Obtener Fuentes')
                {

                    steps
                            {
                                dir(D_WORKSPACE) {
                                    checkout([$class                           : 'GitSCM', branches: [[name: "master"]],
                                              doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [
                                            [credentialsId: "5876d844-88f0-43d5-a955-4722d26b17cc", url: "http://10.30.30.248/gjimenez/Get_External_Link"]
                                    ]])
                                }
                            }
                }

        stage('Ejecutar Pruebas') {
            steps {
                dir(D_WORKSPACE) {
                    script {
                        try {
                            //bat("gradle clean test --tests 'runners.ModuloR4.EjecutarModulR4' -Dtags='@${params.NombreAPI}' aggregate")
                            //bat("gradle clean test --tests *${params.Runner}* -Dtags=\"@${params.NombreAPI}\" aggregate")
                            bat('gradle clean test --tests "runners.ModuloR4.EjecutarModulR4" aggregate');
                            echo 'Test Ejecutados sin Fallo'
                            currentBuild.result = 'SUCCESS'
                        }
                        catch (ex) {
                            echo 'Test Ejecutados con Fallo'
                            currentBuild.result = 'UNSTABLE'
                        }
                    }
                }
            }
        }

        stage('Generar evidencia'){
            steps
                    {
                        dir(D_WORKSPACE) {
                            script
                                    {
                                        try {
                                            bat " rename \"${D_WORKSPACE}\\target\\site\\serenity\" serenity_${timestamp}"
                                            echo 'Backup de evidencias realizado con exito'

                                            publishHTML([
                                                    allowMissing         : false,
                                                    alwaysLinkToLastBuild: true,
                                                    keepAll              : true,
                                                    reportDir            : "${D_WORKSPACE}//target//site//serenity_${timestamp}",
                                                    reportFiles          : 'index.html',
                                                    reportName           : 'Evidencias Automatizacion APIs Banbif',
                                                    reportTitles         : 'APIs BANBIF PERU'
                                            ])
                                            echo 'Reporte Html realizado con exito'
                                        }
                                        catch (e) {
                                            echo 'No se realizo el Backup de evidencias'
                                            publishHTML([allowMissing: false, alwaysLinkToLastBuild: true, keepAll: true, reportDir: "${WORKSPACE}//target//site//serenity_${timestamp}", reportFiles: 'index.html', reportName: 'Evidencias Automatizacion APIs Banbif', reportTitles: 'APIs BANBIF PERU'])
                                            echo 'Reporte Html realizado con exito'
                                            currentBuild.result = 'SUCCESS'
                                        }
                                    }
                        }
                    }
        }

        stage('Notificar') {
            steps {
                script {
                    if (currentBuild.result == 'UNSTABLE')
                        currentBuild.result = 'FAILURE'
                    if(params.isProd)
                    {
                        CORREO = CORREOEXITO;
                    }
                    else
                    {
                        CORREO = CORREOFALLAS;
                    }
                    //def requestResponse = httpRequest timeout: 30000, url:"http://bif1tlb10.dombif.peru/bugzilla/rest/bug";
                    //println("Status: "+response.status)
                    //println("Content: "+response.content)
                    //println(bat("obtenerBugs"))
                    if (currentBuild.result == 'SUCCESS')
                        emailext(
                                subject: "[${params.Ambiente} API ${params.NombreAPI}#${env.BUILD_NUMBER}] TI-EA2023-12608-R3 Compliance getExtenalLink",
                                body: """Cordial saludo, <br><br> Tras ejecutar las pruebas automatizadas se emite el siguiente reporte de <b style="color:MediumSeaGreen;">EJECUCION EXITOSA.</b><br><br><b>Para verificar el estado de la ejecucion siga el siguiente </b><a href='${env.BUILD_URL}'>enlace con destino a ${params.NombreAPI}#${env.BUILD_NUMBER}</a></p>${firmaCorreo}""",
                                to: "${CORREO}"
                        )
                    if (currentBuild.result == 'FAILURE')
                        emailext(
                                subject: "[${params.Ambiente} API ${params.NombreAPI}#${env.BUILD_NUMBER}}] TI-EA2023-12608-R3 Compliance getExtenalLink",
                                body: """Cordial saludo, <br><br>Tras ejecutar las pruebas automatizadas se emite el siguiente reporte de <b style="color:red;">EJECUCION FALLIDA:</b>.<br><br><b>Para verificar el estado de la ejecucion siga el siguiente </b> <a href='${env.BUILD_URL}'>enlace con destino a ${params.NombreAPI}#${env.BUILD_NUMBER}</a></p>${firmaCorreo}""",
                                to: "${CORREO}"
                        )
                }
            }
        }
    }
}