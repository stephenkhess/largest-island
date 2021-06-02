load("@io_bazel_rules_groovy//groovy:groovy.bzl", "groovy_library", "groovy_test", "groovy_and_java_library")

groovy_and_java_library(
    name = "prodlib",
    srcs = glob(["src/main/groovy/**/*.groovy", "src/main/java/**/*.java"]),
)

groovy_library(
    name = "testlib",
    srcs = glob(["src/test/groovy/**/*.groovy"]),
    deps = [":prodlib", "//external:spock"],
)

groovy_test(
    name = "tests",
    srcs = glob(["src/test/groovy/**/*.groovy"]),
    deps = [":testlib", ":prodlib"],
)