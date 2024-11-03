cmake_minimum_required(VERSION 3.4.1)

# Define a library to build from the C++ source file
add_library(
        native-lib       # Name of the library
        SHARED           # Shared library
        src/main/cpp/native-lib.cpp  # Path to C++ source file
)

# Link to the log library for Android logging
find_library(
        log-lib
        log
)

# Link the native library with the Android log library
target_link_libraries(
        native-lib
        ${log-lib}
)