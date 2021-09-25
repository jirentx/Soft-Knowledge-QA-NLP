package com.cnn.cnnn;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.apache.lucene.analysis.tokenattributes.FlagsAttribute;
import org.apache.lucene.search.ScoreCachingWrappingScorer;

import com.cnn.entity.