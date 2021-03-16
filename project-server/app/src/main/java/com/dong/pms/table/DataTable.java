package com.dong.pms.table;

import com.dong.util.Request;
import com.dong.util.Response;

public interface DataTable {
  void service(Request request, Response response) throws Exception;
}
