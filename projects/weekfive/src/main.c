#include <stdio.h>
#include <dirent.h>
#include <sys/types.h>


// reference: https://stackoverflow.com/questions/612097/how-can-i-get-the-list-of-files-in-a-directory-using-c-or-c
int main(int argc, char** argv) { 
    list_dir("myFolderName");
    return EXIT_SUCCESS;
}  

static void list_dir(const char *path) {
    struct dirent *entry;
    DIR *dir = opendir(path);
    if (dir == NULL) {
        return;
    }

    while ((entry = readdir(dir)) != NULL) {
        printf("%s\n",entry->d_name);
    }

    closedir(dir);
}
