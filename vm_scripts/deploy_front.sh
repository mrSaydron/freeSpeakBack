#!/bin/bash

artefacts="$(curl -s 'https://api.github.com/repos/mrSaydron/freeSpeakFront/actions/artifacts')"

function parse {
ARTEFACTS="$1" python3 - <<END
import sys, json, os
artefacts = os.environ['ARTEFACTS']
print(json.loads(artefacts)['artifacts'][0]['archive_download_url'])
END
}

url="$(parse "$artefacts")"
echo $url

curl \
-L \
-o build_result.zip \
-H "Accept: application/vnd.github.v3+json" \
-H "Authorization: token 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" \
$url

sudo mkdir /web-app
sudo unzip -o build_result.zip -d /web-app
