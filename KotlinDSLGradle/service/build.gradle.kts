repositories {
    mavenCentral()
}

tasks.bootJar{
    enabled = false
}

tasks.jar{
    enabled = true
}