# Week 5 - Malware

This section contains two subsections because the initial plan was to create the
simulated ransomware attack in C, which proved unfeasible due to an inability to
get the necessary packages working in VSCode in a timely manner. As such, please
ignore the `src-c` folder at this point in time. Instead, a Python program was
created and stored in `src-py`.

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