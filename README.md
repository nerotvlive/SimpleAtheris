# SimpleAtheris
SimpleAtheris is a core paper Plugin for Atheris Studios' Minecraft roleplay projects written in Java.<br>
Authors: [nerotvlive](https://github.com/nerotvlive)<br>
License: [GNU GPL 3.0](https://github.com/nerotvlive/SimpleAtheris?tab=GPL-3.0-1-ov-file)

> Warning: this plugin is in a very early development stage. Expect many bugs and incomplete features.

## Features

- Lightweight chat extension
    - Supports local (normal), global, shout and whisper chat modes
    - Formats and ranges configurable via `config.yml`
- Console and player commands
    - Management commands for broadcast, global chat, character name, reveal, shout, whisper, and more
- Configuration-driven localization
    - External language files (e.g. `plugins/atheris/lang/de.yml`) are loaded and populated with defaults
- Flexible storage
    - Supports YAML, SQLite or MariaDB/MySQL storage (configurable)
- Utilities
    - Simple `Countdown` helper for scheduled actions
    - `Config` wrapper for easy access to `YamlConfiguration`

## Commands (overview)

Registered commands (see `plugin.yml`):
- `/atheris` — main/system command
- `/book` — book related utilities
- `/broadcast` (`/bc`) — server-wide messages
- `/global` (`/g`) — global chat
- `/name` (`/char`/`/character`) — character / name management
- `/reveal` — reveal / visibility utilities
- `/shout` (`/s`) — shout chat
- `/whisper` (`/w`) — whisper chat

Note: Many command handlers are placeholders and need project-specific implementation.

## Configuration

- The default configuration is `config.yml` and is copied to `plugins/atheris/config.yml` on first run.
- Important paths/options:
    - `config.general.chat.*` — formats, ranges and enable flags
    - `config.system.general.language` — path to language file (e.g. `plugins/atheris/lang/de.yml`)
    - `config.system.saver.*` — storage options (YAML / MariaDB / SQLite)
- The plugin initialization will create and populate default entries if missing.

## Installation

1. Copy the jar to the `plugins` folder of your Paper server.
2. Start the server — configuration files are generated on first start.
3. Adjust `plugins/atheris/config.yml` as needed and restart the server.
