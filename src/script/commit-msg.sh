#!/bin/sh
set -eu

MSG_FILE="$1"
FIRST_LINE=$(head -n1 "$MSG_FILE")

PATTERN='^(feat|fix|docs|style|refactor|test|chore)(\(.+\))?: .+'

if ! printf '%s\n' "$FIRST_LINE" | grep -qE "$PATTERN"; then
  echo "ERROR: Conventional Commits required on the first line."
  echo "Examples:"
  echo "  feat: add user login endpoint"
  echo "  fix(auth): handle expired refresh tokens"
  exit 1
fi
