# Week 5 - Malware

This section contains two subsections because the initial plan was to create the
simulated ransomware attack in C, which proved unfeasible due to an inability to
get the necessary packages working in VSCode in a timely manner. As such, please
ignore the `src-c` folder at this point in time. Instead, a Python program was
created and stored in `src-py`.

This program is created to mimic ransomware without actually being capable of
harming the user. As such, it is confined to affecting the files within this
project, and is hardcoded to only have access to the example files included
here. At no point is anything outside the `src-py` folder affected, and at
no point is actual payment accepted.

## Python

In order to run the program, navigate into `src-py` and run the following
command:

- `python main.py`

This may require creating a virtual environment to install dependencies, as
this is not a fully fleshed out project and thus lacks the convenience of
the `poetry install`.

Dependencies:

- `os`
- `pathlib`
- `typing` 
- `cryptography.fernet`
- `rich.console`

With this complete, the expected output is either:

```text
Running safe program...
Surprise! Your files have been encrypted.
They would be deleted here if this was a malicious attack.
And here's where money gets requested. Pay me? Y/N Y
Thank you! Decrypting now.
```

or

```text
Running safe program...
Surprise! Your files have been encrypted.
They would be deleted here if this was a malicious attack.
And here's where money gets requested. Pay me? Y/N N
Files remain encrypted.
```

The process is expected to delete the files in `src-py/example-files`. These
files are also present in the `src-py/backup` folder, so that they may be
copied back into `src-py/example-files` for another run. Each run is likely to
overwrite the files in `src-py/encrypted`, though this is not guaranteed. The
`src-py/decrypted` folder should ultimately contain the same files that were in
`src-py/example-files` when the program was last run if the output is the same
as the first example. Otherwise, `src-py/decrypted` will be empty or hold
outdated files, in cases where it is not cleaned between runs.