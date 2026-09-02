#!/usr/bin/env bash
set -uo pipefail

echo "======== Git History ========="

# Find the first matching commit (in case of multiple matches, take the earliest)
GIT_COMMIT=$(git log --all --oneline | grep 'impl' | tail -1 | cut -d' ' -f1)

if [ -z "$GIT_COMMIT" ]; then
  echo 'Something was wrong with GIT_COMMIT'
  exit 1
fi

# Handle case where GIT_COMMIT has no parent (first commit in repo)
if git rev-parse "${GIT_COMMIT}^" >/dev/null 2>&1; then
  PARENT="${GIT_COMMIT}^"
else
  PARENT=$(git hash-object -t tree /dev/null)
fi

# Run the diff once, reuse the output
DIFF_OUTPUT=$(git diff "$PARENT..HEAD" --diff-filter=A --name-only)
DIFF_STATUS=$?

if [ $DIFF_STATUS -ne 0 ]; then
  echo 'Something was wrong!'
  exit 1
fi

TOTAL_FILES_ADDED=$(echo "$DIFF_OUTPUT" | grep -c '.')
FILE_NAME="$DIFF_OUTPUT"

echo 'Good so far!'
echo "Files added: $TOTAL_FILES_ADDED"
echo "Name: $FILE_NAME"