plugins {
    id("dev.slne.surf.surfapi.gradle.paper-plugin") version "1.21.11+"
}

surfPaperPluginApi {
    mainClass("dev.slne.surf.gift.PaperMain")
    generateLibraryLoader(false)
    foliaSupported(true)

    authors.add("Jo_field")

    withSurfDatabaseR2dbc("1.3.0", "dev.slne.surf.gift.libs")
}

version = findProperty("version") as String
group = "dev.slne.surf.gift"