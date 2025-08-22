#!/bin/sh
set -eu

# ANSI color codes
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
BOLD='\033[1m'
NC='\033[0m' # No Color

MSG_FILE="$1"
FIRST_LINE=$(head -n1 "$MSG_FILE")

# The Conventional Commits pattern
PATTERN='^(feat|fix|docs|style|refactor|test|chore)(\(.+\))?!?: .+'

if ! printf '%s\n' "$FIRST_LINE" | grep -qE "$PATTERN"; then
  # Fancy, detailed error message
  printf "\n${RED}${BOLD}========================= INVALID COMMIT MESSAGE =========================${NC}\n"
  printf "${RED}Error:${NC} Your commit message does not follow the Conventional Commits standard.\n\n"

  printf "${YELLOW}The standard format is:${NC}\n"
  printf "  ${GREEN}<type>${NC}(${BLUE}<optional scope>${NC}): ${BOLD}<subject>${NC}\n\n"

  printf "${YELLOW}Your message was:${NC}\n"
  printf "  ${RED}➜ %s${NC}\n\n" "$FIRST_LINE"

  printf "${YELLOW}Please correct your message. Here are the rules:${NC}\n"
  printf "  ${BOLD}1. <type>:${NC} Must be one of the following:\n"
  printf "     ${GREEN}feat${NC}:      A new feature\n"
  printf "     ${GREEN}fix${NC}:       A bug fix\n"
  printf "     ${GREEN}docs${NC}:      Documentation only changes\n"
  printf "     ${GREEN}style${NC}:     Changes that do not affect the meaning of the code\n"
  printf "     ${GREEN}refactor${NC}:  A code change that neither fixes a bug nor adds a feature\n"
  printf "     ${GREEN}test${NC}:      Adding missing tests or correcting existing tests\n"
  printf "     ${GREEN}chore${NC}:     Changes to the build process or auxiliary tools\n"

  printf "\n  ${BOLD}2. <scope>:${NC} (Optional) A noun specifying the section of the codebase.\n"
  printf "     Example: ${GREEN}fix${NC}(${BLUE}api${NC}), ${GREEN}feat${NC}(${BLUE}auth${NC})\n"

  printf "\n  ${BOLD}3. <subject>:${NC} A short, imperative-tense description of the change.\n"
  printf "     - Start with a lowercase letter.\n"
  printf "     - Do NOT end with a period.\n"

  printf "\n${YELLOW}Correct Examples:${NC}\n"
  printf "  ${GREEN}feat${NC}: add user login endpoint\n"
  printf "  ${GREEN}fix${NC}(${BLUE}auth${NC}): handle expired refresh tokens\n"

  printf "\n${RED}${BOLD}========================================================================${NC}\n\n"

  exit 1
fi