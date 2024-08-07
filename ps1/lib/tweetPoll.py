#!/usr/bin/python

import oauth2 as oauth
import json
import cgi
import sys
import os
import time
from datetime import timedelta

# Written by wvyar@mit for Spring 2014.
# Throwaway Twitter account used for this script:
# username: 7iBUHvauhQ
# password: DPaCHWEemhodoan3Owvs

def constructURL(base_url, params):
    """ Constructs a URL from a base URL and a dictionary of parameters. """

    constructed_url = base_url + "?"
    for k,v in params.iteritems():
        constructed_url += k + "=" + v + "&"
    return constructed_url[:-1]

def shouldPoll(filename):
    """ Checks the modification time of the JSON file. Returns a boolean
        indicating if it has been more than an hour since it was last
        modified. """
    try:
        td = timedelta(seconds=time.time()-os.stat(filename).st_mtime)
        return td > timedelta(hours=1)
    except:
        return True

def pollTweets():
    """ Contains the information for accessing the Twitter API. Calls
        shouldPoll to determine if we need to refresh our tweet database.
        If it has been more than an hour since our last poll, it iterates
        over the search terms and compiles a list of statuses that should
        be written to disk. Finally, it writes the contents of the file to
        stdout. """

    ACCESS_KEYS = { 
            "CONSUMER_KEY":'0B8ikJQ6KtJv3j0Qutx7A',
            "CONSUMER_SECRET":'Fp8YnhucDRGKRBXRJZkeG7bcPinkO3KHkTo6ufiAc',
            "ACCESS_TOKEN":'2337270692-ms4pEF81l3JageDGS6lAJYJPEkiaiUIooeD2PVt',
            "ACCESS_TOKEN_SECRET":'5TIVRdPkptGXhWaJmuI5u7CszMFzYJIfzZgSLIR8Z2a11'
            }
    FILENAME = "tweets.json"
    URL = 'https://api.twitter.com/1.1/search/tweets.json'
    SEARCH_TERMS = ["olympics", "bitcoin", "mit", "obama"]
    PARAMS = { "lang":"en", "count":"100" }
    NUM_PAGES = 10

    #pollTime = shouldPoll(FILENAME)
    if shouldPoll(FILENAME):
        statuses = []
        for term in SEARCH_TERMS:
            PARAMS["q"] = term
            statuses += twitterSearch(NUM_PAGES, URL, PARAMS, ACCESS_KEYS)
        writeTweetsOut(FILENAME, removeRepeats(statuses))
    sys.stdout.write( file(FILENAME, "rb").read() )

def twitterSearch(numPages, url, params, keys):
    """ Generates numPages requests to the Twitter API. Uses max_id to ensure
        that the Tweets collected are unique. Returns the list containing all
        of the retrieved statuses. """

    FIELD_NAMES = ["created_at", "id",
                   "text", "user.screen_name"]
    statuses = []
    for i in range(numPages):
        curStats = json.loads(
                    oauth_req(
                        constructURL(url, params),
                        keys["ACCESS_TOKEN"],
                        keys["ACCESS_TOKEN_SECRET"],
                        keys["CONSUMER_KEY"],
                        keys["CONSUMER_SECRET"]
                    )
                )["statuses"]
        params["max_id"] = str(curStats[-1]["id"])
        for status in curStats:
            statuses.append(pullRelevantFields(FIELD_NAMES, status))
    return statuses

def pullRelevantFields(fieldNames, status):
    """ Takes a list of fieldNames and a status and returns a dictionary
        object containing only those fields. To access subfields, the a
        period is used (e.g., user.screen_name). """
    trimmedStatus = {}
    for field in fieldNames:
        subfields = field.split(".")
        trimmedStatus[field] = getSubfields(subfields, status)
    return trimmedStatus

def getSubfields(subfields, statusField):
    """ Helper method for pullRelevantFields: recurses to pull out the
        correct subfield. """
    if len(subfields) == 1:
        return statusField[subfields[0]]
    else:
        return getSubfields(subfields[1:], statusField[subfields[0]])

def removeRepeats(tweetList):
    """ Takes in a list of tweets and returns all the unique tweets.
        Assumes that the ID field is present in each tweet. """
    seenTweetIDs = set()
    prunedTweets = []
    for tweet in tweetList:
        if not tweet["id"] in seenTweetIDs:
            seenTweetIDs.add(tweet["id"])
            prunedTweets.append(tweet)
    return prunedTweets

def oauth_req(url, key, secret, consumer_key, consumer_secret, 
        http_method="GET", post_body=None, http_headers=None):
    """ Uses the OAuth2 library written by Brian Rosner to make a request.
        https://github.com/brosner/python-oauth2 """

    consumer = oauth.Consumer(key=consumer_key, secret=consumer_secret)
    token = oauth.Token(key=key, secret=secret)
    client = oauth.Client(consumer, token, disable_ssl_certificate_validation=True)

    resp, content = client.request(
            url,
            method=http_method,
            body=post_body,
            headers=http_headers,
            force_auth_header=True,
            )
    return content

def writeTweetsOut(filename, data):
    """ Opens the filename and dumps the JSON data. """

    f = open(filename, 'w')
    f.write(json.dumps(data, sort_keys=True, indent=4, separators=(',', ': ')))
    f.close()

def main():
    sys.stdout.write("\n")
    pollTweets()

if __name__ == "__main__":
    main()