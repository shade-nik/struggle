package local.halflight.learning.util;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

import org.hibernate.cfg.NotYetImplementedException;

import com.google.common.collect.Lists;

public class ConverterUtil {

	
	public static <FROM, TO> List<TO> convertList(List<FROM> from, Function<FROM, TO> func)
	{
		return from.stream().map(func).collect(Collectors.toList());
	}

	public static <FROM, TO> TO[] convertArray(FROM[] from, Function<FROM, TO> func, 
            IntFunction<TO[]> generator){
		return Arrays.stream(from).map(func).toArray(generator);
	}

	public <FROM, TO> List<TO> transformList(List<FROM> fromList, Function<FROM,TO> function )
	{
//		Lists.transform(fromList, function);
		throw new NotYetImplementedException();
	}
}


