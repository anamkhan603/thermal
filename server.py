import flask
import random

import sys
import os
import glob
import re
from pathlib import Path
import pickle
import numpy as np


# Import fast.ai Library
from fastai import *
from fastai.vision import *

# Flask utils
from flask import Flask, redirect, url_for, request, render_template
from werkzeug.utils import secure_filename

app = flask.Flask(__name__)
UPLOAD_FOLDER = './UPLOAD_FOLDER/'

path=Path("path")

classes = ['stress', 'non-stress']

learn=load_learner(path,'a.pkl')


with open('classifier_pickle','rb') as f:
    cls=pickle.load(f)

def model_predict(img_path):
    """model_predict will return the preprocessed image
    """
    img = open_image(img_path)
    pred_class,pred_idx,outputs = learn.predict(img)
    return pred_class

@app.route('/upload', methods = ['GET', 'POST'])
def handle_request():
    print("hello");
    imagefile = flask.request.files['image']
    print("hello", flask.request);
    filename = UPLOAD_FOLDER + str(random.randint(0, 5000)) + '.png'
    #filename = werkzeug.utils.secure_filename(imagefile.filename)
    #filename= "photo.jpg";
    print("\nReceived image File name : " + imagefile.filename)
    imagefile.save(filename)

    preds=model_predict(filename)
    print(type(preds))

    return str(preds)


@app.route('/calculate', methods = ['GET', 'POST'])
def handle_response():
	print("Hello");
	stringValues= flask.request.values.get['dry', 'wet', 'canopy', 'time']
	#print("Hello", flask.request);

	pred=np.array([stringValues])
	ans=cls.predict(pred)
	return str(ans)


app.run(host="127.0.0.1",port=5000, debug=True)