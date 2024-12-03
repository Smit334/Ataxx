# Ataxx

Ataxx is a strategy board game where players aim to dominate the board by converting opponent pieces to their own. This repository contains the implementation of the Ataxx game.

## Features

- Single-player and multiplayer modes
- Various difficulty levels for AI opponents
- Command-line interface for gameplay
- Debugging utilities for developers

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/Ataxx.git
    ```
2. Navigate to the project directory:
    ```sh
    cd Ataxx
    ```

### Building the Project

To compile the project, run:
```sh
javac -d bin src/ataxx/*.java
```

### Running the Game

To run the game, use the following command:
```sh
java -cp bin ataxx.Main
```

## Usage

### Command-Line Options

- `-h` or `--help`: Display help information
- `-s` or `--singleplayer`: Start a single-player game
- `-m` or `--multiplayer`: Start a multiplayer game
- `-d` or `--difficulty [level]`: Set the difficulty level for AI opponents (easy, medium, hard)

### Example Commands

Start a single-player game:
```sh
java -cp bin ataxx.Main -s
```

Start a multiplayer game:
```sh
java -cp bin ataxx.Main -m
```

Start a single-player game with medium difficulty:
```sh
java -cp bin ataxx.Main -s -d medium
```

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a new branch (`git checkout -b feature-branch`)
3. Commit your changes (`git commit -m 'Add some feature'`)
4. Push to the branch (`git push origin feature-branch`)
5. Open a pull request

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact

For any questions or feedback, please open an issue on the repository or contact the maintainer at [Smit334](https://github.com/Smit334).